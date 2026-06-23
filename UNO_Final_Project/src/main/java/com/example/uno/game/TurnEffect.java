package com.example.uno.game;

public record TurnEffect(
        int nextPlayerIndex,
        int newDirection,
        int cardsToDraw,
        int affectedPlayerIndex,
        boolean affectedPlayerLosesTurn
) {
}
