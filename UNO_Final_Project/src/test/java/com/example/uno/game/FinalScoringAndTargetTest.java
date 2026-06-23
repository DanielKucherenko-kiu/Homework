package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.CardType;
import com.example.uno.domain.Player;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinalScoringAndTargetTest {
    @Test
    void playerHandScoreUsesUnoCardValues() {
        Player player = new Player("Bob", false);

        player.addCard(Card.number(CardColor.RED, 9));
        player.addCard(Card.action(CardColor.BLUE, CardType.SKIP));
        player.addCard(Card.action(CardColor.GREEN, CardType.DRAW_TWO));
        player.addCard(Card.wild());

        assertEquals(99, player.handScore());
    }

    @Test
    void roundResultStoresWinnerPointsAndCumulativeScores() {
        RoundResult result = new RoundResult(
                1,
                "Alice",
                45,
                Map.of("Alice", 45, "Bob", 0)
        );

        assertEquals(1, result.getRoundNumber());
        assertEquals("Alice", result.getWinnerName());
        assertEquals(45, result.getPointsAwarded());
        assertEquals(45, result.getCumulativeScores().get("Alice"));
    }

    @Test
    void gameResultStoresFinalWinnerRoundsAndScores() {
        RoundResult round = new RoundResult(
                1,
                "Alice",
                100,
                Map.of("Alice", 100, "Bob", 0)
        );

        GameResult result = new GameResult(
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                "Alice",
                1,
                Map.of("Alice", 100, "Bob", 0),
                List.of(round)
        );

        assertEquals("Alice", result.getWinnerName());
        assertEquals(1, result.getTotalRounds());
        assertEquals(100, result.getFinalScores().get("Alice"));
        assertEquals(1, result.getRounds().size());
    }
}
