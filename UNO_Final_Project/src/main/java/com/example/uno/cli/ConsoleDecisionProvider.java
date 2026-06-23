package com.example.uno.cli;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.Player;
import com.example.uno.game.PlayerDecisionProvider;
import com.example.uno.game.UnoRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleDecisionProvider implements PlayerDecisionProvider {
    private static final Logger log = LoggerFactory.getLogger(ConsoleDecisionProvider.class);

    private final Scanner scanner;

    public ConsoleDecisionProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Card chooseCardToPlay(Player player, Card topCard, CardColor activeColor, List<Card> legalCards) {
        if (player.isBot()) {
            return legalCards.isEmpty() ? null : legalCards.get(0);
        }

        System.out.println();
        System.out.println("Top card: " + topCard + " | Active color: " + activeColor);
        System.out.println(player.getName() + "'s hand:");
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            String marker = UnoRules.canPlay(card, topCard, activeColor) ? " *legal*" : "";
            System.out.printf("  %d) %s%s%n", i + 1, card, marker);
        }

        while (true) {
            System.out.print("Choose card number, or D to draw/pass: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("D")) {
                return null;
            }
            try {
                int index = Integer.parseInt(input) - 1;
                if (index < 0 || index >= hand.size()) {
                    System.out.println("That card number is outside your hand.");
                    log.warn("Invalid input: card index outside hand");
                    continue;
                }
                Card selected = hand.get(index);
                if (!UnoRules.canPlay(selected, topCard, activeColor)) {
                    System.out.println("That card is not legal here. Pick a legal card or draw.");
                    log.warn("Invalid input: illegal card selected");
                    continue;
                }
                return selected;
            } catch (NumberFormatException ex) {
                System.out.println("Please type a card number or D.");
                log.warn("Invalid input: not a card number or D");
            }
        }
    }

    @Override
    public boolean shouldPlayDrawnCard(Player player, Card drawnCard, Card topCard, CardColor activeColor) {
        if (player.isBot()) {
            return true;
        }
        System.out.println("You drew: " + drawnCard);
        if (!UnoRules.canPlay(drawnCard, topCard, activeColor)) {
            System.out.println("It is not playable, so your turn passes.");
            return false;
        }
        return askYesNo("It is playable. Play it now? (y/n): ");
    }

    @Override
    public CardColor chooseColor(Player player, Card wildCard) {
        if (player.isBot()) {
            return chooseBotColor(player);
        }
        while (true) {
            System.out.print("Choose color (red/yellow/green/blue): ");
            String input = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
            try {
                CardColor color = CardColor.valueOf(input);
                if (color.isPlayableColor()) {
                    return color;
                }
            } catch (IllegalArgumentException ignored) {
            }
            System.out.println("Please choose red, yellow, green, or blue.");
            log.warn("Invalid input: bad wild color");
        }
    }

    @Override
    public boolean shouldCallUno(Player player) {
        if (player.isBot()) {
            player.callUno();
            return true;
        }
        return askYesNo("You have one card. Call UNO? (y/n): ");
    }

    private CardColor chooseBotColor(Player player) {
        Map<CardColor, Long> colorCounts = player.getHand().stream()
                .filter(card -> card.getColor().isPlayableColor())
                .collect(Collectors.groupingBy(Card::getColor, Collectors.counting()));
        return colorCounts.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(CardColor.RED);
    }

    private boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("Please answer y or n.");
            log.warn("Invalid input: expected yes/no");
        }
    }
}
