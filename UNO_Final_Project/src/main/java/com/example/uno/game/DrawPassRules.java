package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;

public final class DrawPassRules {
    private DrawPassRules() {
    }

    public static boolean mayPlayDrawnCard(Card drawnCard,
                                           Card topCard,
                                           CardColor activeColor,
                                           DrawPassPolicy policy) {
        if (policy == DrawPassPolicy.DRAW_ONE_AND_PASS) {
            return false;
        }

        return UnoRules.canPlay(drawnCard, topCard, activeColor);
    }
}
