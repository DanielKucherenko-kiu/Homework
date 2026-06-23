package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardType;

public final class TurnEngine {
    private TurnEngine() {
    }

    public static TurnEffect resolvePlayedCard(Card playedCard,
                                               int currentPlayerIndex,
                                               int currentDirection,
                                               int playerCount) {
        if (playerCount < 2) {
            throw new IllegalArgumentException("UNO needs at least two players.");
        }

        if (currentDirection != 1 && currentDirection != -1) {
            throw new IllegalArgumentException("Direction must be 1 or -1.");
        }

        CardType type = playedCard.getType();

        return switch (type) {
            case NUMBER, WILD -> normalTurn(currentPlayerIndex, currentDirection, playerCount);
            case SKIP -> skipTurn(currentPlayerIndex, currentDirection, playerCount);
            case REVERSE -> reverseTurn(currentPlayerIndex, currentDirection, playerCount);
            case DRAW_TWO -> drawAndSkip(currentPlayerIndex, currentDirection, playerCount, 2);
            case WILD_DRAW_FOUR -> drawAndSkip(currentPlayerIndex, currentDirection, playerCount, 4);
        };
    }

    private static TurnEffect normalTurn(int currentPlayerIndex, int direction, int playerCount) {
        return new TurnEffect(
                advance(currentPlayerIndex, direction, 1, playerCount),
                direction,
                0,
                -1,
                false
        );
    }

    private static TurnEffect skipTurn(int currentPlayerIndex, int direction, int playerCount) {
        int skippedPlayer = advance(currentPlayerIndex, direction, 1, playerCount);

        return new TurnEffect(
                advance(currentPlayerIndex, direction, 2, playerCount),
                direction,
                0,
                skippedPlayer,
                true
        );
    }

    private static TurnEffect reverseTurn(int currentPlayerIndex, int direction, int playerCount) {
        if (playerCount == 2) {
            int skippedPlayer = advance(currentPlayerIndex, direction, 1, playerCount);

            return new TurnEffect(
                    advance(currentPlayerIndex, direction, 2, playerCount),
                    direction,
                    0,
                    skippedPlayer,
                    true
            );
        }

        int newDirection = -direction;

        return new TurnEffect(
                advance(currentPlayerIndex, newDirection, 1, playerCount),
                newDirection,
                0,
                -1,
                false
        );
    }

    private static TurnEffect drawAndSkip(int currentPlayerIndex,
                                          int direction,
                                          int playerCount,
                                          int cardsToDraw) {
        int affectedPlayer = advance(currentPlayerIndex, direction, 1, playerCount);

        return new TurnEffect(
                advance(currentPlayerIndex, direction, 2, playerCount),
                direction,
                cardsToDraw,
                affectedPlayer,
                true
        );
    }

    public static int advance(int currentPlayerIndex, int direction, int steps, int playerCount) {
        int result = currentPlayerIndex;

        for (int i = 0; i < steps; i++) {
            result = Math.floorMod(result + direction, playerCount);
        }

        return result;
    }
}
