package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.CardType;

public final class UnoRules {
    private UnoRules() {
    }

    public static boolean canPlay(Card candidate, Card topCard, CardColor activeColor) {
        if (candidate.isWild()) {
            return true;
        }
        if (candidate.getColor() == activeColor) {
            return true;
        }
        if (candidate.getType() == CardType.NUMBER && topCard.getType() == CardType.NUMBER) {
            return candidate.getNumber() == topCard.getNumber();
        }
        return candidate.getType() != CardType.NUMBER && candidate.getType() == topCard.getType();
    }
}
