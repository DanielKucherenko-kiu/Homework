package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.CardType;
import com.example.uno.domain.Deck;
import com.example.uno.domain.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class UnoGame {
    private static final Logger log = LoggerFactory.getLogger(UnoGame.class);
    private static final int STARTING_HAND_SIZE = 7;
    private static final int MISSED_UNO_PENALTY_CARDS = 2;

    private final List<Player> players;
    private final int targetScore;
    private final Random random;

    public UnoGame(List<Player> players, int targetScore) {
        this(players, targetScore, new Random());
    }

    public UnoGame(List<Player> players, int targetScore, Random random) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("UNO needs at least two players.");
        }
        if (targetScore < 1) {
            throw new IllegalArgumentException("Target score must be positive.");
        }
        this.players = new ArrayList<>(players);
        this.targetScore = targetScore;
        this.random = random;
    }

    public GameResult play(PlayerDecisionProvider decisions) {
        LocalDateTime startedAt = LocalDateTime.now();
        log.info("Game start with {} players and target score {}", players.size(), targetScore);

        List<RoundResult> roundResults = new ArrayList<>();
        int roundNumber = 1;
        Player gameWinner = null;

        while (gameWinner == null) {
            RoundResult roundResult = playRound(roundNumber, decisions);
            roundResults.add(roundResult);
            gameWinner = players.stream()
                    .filter(player -> player.getTotalScore() >= targetScore)
                    .max(Comparator.comparingInt(Player::getTotalScore))
                    .orElse(null);
            roundNumber++;
        }

        Map<String, Integer> scores = currentScoreMap();
        LocalDateTime endedAt = LocalDateTime.now();
        log.info("Game end. Winner: {}. Final scores: {}", gameWinner.getName(), scores);
        return new GameResult(startedAt, endedAt, gameWinner.getName(), roundResults.size(), scores, roundResults);
    }

    private RoundResult playRound(int roundNumber, PlayerDecisionProvider decisions) {
        log.info("Round {} start", roundNumber);
        Deck deck = Deck.standardShuffled(random);
        List<Card> discardPile = new ArrayList<>();
        for (Player player : players) {
            player.clearHand();
            for (int i = 0; i < STARTING_HAND_SIZE; i++) {
                player.addCard(deck.draw());
            }
        }

        Card topCard = drawStartingDiscard(deck, discardPile);
        discardPile.add(topCard);
        CardColor activeColor = topCard.getColor();
        int currentIndex = 0;
        int direction = 1;

        while (true) {
            Player player = players.get(currentIndex);
            log.info("Player turn: {}. Top card: {}. Active color: {}", player.getName(), topCard, activeColor);

            applyMissedUnoPenaltyIfNeeded(player, deck, discardPile);

            List<Card> legalCards = legalCards(player, topCard, activeColor);
            Card selected = decisions.chooseCardToPlay(player, topCard, activeColor, legalCards);
            boolean played = false;

            if (selected != null) {
                if (!player.getHand().contains(selected) || !UnoRules.canPlay(selected, topCard, activeColor)) {
                    log.warn("Invalid input/play attempt by {}: {}", player.getName(), selected);
                } else {
                    player.removeCard(selected);
                    discardPile.add(selected);
                    topCard = selected;
                    played = true;
                    log.info("Card played: {} played {}", player.getName(), selected);
                    activeColor = selected.isWild() ? decisions.chooseColor(player, selected) : selected.getColor();
                    if (player.handSize() == 1 && decisions.shouldCallUno(player)) {
                        player.callUno();
                    }
                    if (player.handSize() == 0) {
                        return finishRound(roundNumber, player);
                    }
                    currentIndex = nextIndexAfterPlayedCard(currentIndex, direction, selected, deck, discardPile, activeColor);
                    if (selected.getType() == CardType.REVERSE && players.size() > 2) {
                        direction *= -1;
                    }
                }
            }

            if (!played) {
                Card drawn = drawOne(deck, discardPile);
                player.addCard(drawn);
                log.info("Card drawn: {} drew one card", player.getName());

                if (UnoRules.canPlay(drawn, topCard, activeColor)
                        && decisions.shouldPlayDrawnCard(player, drawn, topCard, activeColor)) {
                    player.removeCard(drawn);
                    discardPile.add(drawn);
                    topCard = drawn;
                    log.info("Card played: {} played drawn card {}", player.getName(), drawn);
                    activeColor = drawn.isWild() ? decisions.chooseColor(player, drawn) : drawn.getColor();
                    if (player.handSize() == 1 && decisions.shouldCallUno(player)) {
                        player.callUno();
                    }
                    if (player.handSize() == 0) {
                        return finishRound(roundNumber, player);
                    }
                    currentIndex = nextIndexAfterPlayedCard(currentIndex, direction, drawn, deck, discardPile, activeColor);
                    if (drawn.getType() == CardType.REVERSE && players.size() > 2) {
                        direction *= -1;
                    }
                } else {
                    currentIndex = advance(currentIndex, direction, 1);
                }
            }
        }
    }

    private Card drawStartingDiscard(Deck deck, List<Card> discardPile) {
        Card first = deck.draw();
        while (first.getType() != CardType.NUMBER) {
            discardPile.add(first);
            first = deck.draw();
        }
        if (!discardPile.isEmpty()) {
            List<Card> nonStartingCards = new ArrayList<>(discardPile);
            discardPile.clear();
            deck.addAllAndShuffle(nonStartingCards);
        }
        return first;
    }

    private void applyMissedUnoPenaltyIfNeeded(Player player, Deck deck, List<Card> discardPile) {
        if (player.handSize() == 1 && !player.hasCalledUno()) {
            for (int i = 0; i < MISSED_UNO_PENALTY_CARDS; i++) {
                player.addCard(drawOne(deck, discardPile));
            }
            log.info("Missed UNO penalty: {} drew {} cards", player.getName(), MISSED_UNO_PENALTY_CARDS);
        }
    }

    private RoundResult finishRound(int roundNumber, Player winner) {
        int points = players.stream()
                .filter(player -> player != winner)
                .mapToInt(Player::handScore)
                .sum();
        winner.addScore(points);
        Map<String, Integer> scores = currentScoreMap();
        log.info("Round end. Round {} winner: {}. Points: {}. Scores: {}", roundNumber, winner.getName(), points, scores);
        return new RoundResult(roundNumber, winner.getName(), points, scores);
    }

    private List<Card> legalCards(Player player, Card topCard, CardColor activeColor) {
        return player.getHand().stream()
                .filter(card -> UnoRules.canPlay(card, topCard, activeColor))
                .collect(Collectors.toList());
    }

    private int nextIndexAfterPlayedCard(int currentIndex,
                                         int direction,
                                         Card playedCard,
                                         Deck deck,
                                         List<Card> discardPile,
                                         CardColor activeColor) {
        return switch (playedCard.getType()) {
            case SKIP -> advance(currentIndex, direction, 2);
            case REVERSE -> players.size() == 2 ? advance(currentIndex, direction, 2) : advance(currentIndex, -direction, 1);
            case DRAW_TWO -> {
                Player target = players.get(advance(currentIndex, direction, 1));
                drawCards(target, 2, deck, discardPile);
                yield advance(currentIndex, direction, 2);
            }
            case WILD_DRAW_FOUR -> {
                Player target = players.get(advance(currentIndex, direction, 1));
                drawCards(target, 4, deck, discardPile);
                yield advance(currentIndex, direction, 2);
            }
            case NUMBER, WILD -> advance(currentIndex, direction, 1);
        };
    }

    private void drawCards(Player player, int count, Deck deck, List<Card> discardPile) {
        for (int i = 0; i < count; i++) {
            player.addCard(drawOne(deck, discardPile));
        }
        log.info("Card drawn: {} drew {} cards", player.getName(), count);
    }

    private Card drawOne(Deck deck, List<Card> discardPile) {
        if (deck.isEmpty()) {
            if (discardPile.size() <= 1) {
                throw new IllegalStateException("No cards available to draw.");
            }
            Card top = discardPile.remove(discardPile.size() - 1);
            List<Card> recycle = new ArrayList<>(discardPile);
            discardPile.clear();
            discardPile.add(top);
            deck.addAllAndShuffle(recycle);
        }
        return deck.draw();
    }

    private int advance(int currentIndex, int direction, int steps) {
        int size = players.size();
        int result = currentIndex;
        for (int i = 0; i < steps; i++) {
            result = Math.floorMod(result + direction, size);
        }
        return result;
    }

    private Map<String, Integer> currentScoreMap() {
        Map<String, Integer> scores = new LinkedHashMap<>();
        for (Player player : players) {
            scores.put(player.getName(), player.getTotalScore());
        }
        return scores;
    }
}
