package com.example.uno.game;

import com.example.uno.domain.Player;

public final class UnoPenalty {
    private UnoPenalty() {
    }

    public static boolean shouldApplyMissedUnoPenalty(Player player) {
        return player.handSize() == 1 && !player.hasCalledUno();
    }
}
