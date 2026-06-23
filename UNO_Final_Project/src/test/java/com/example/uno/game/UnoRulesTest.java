package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnoRulesTest {
    @Test
    void cardCanBePlayedByMatchingColor() {
        Card top = Card.number(CardColor.RED, 5);
        Card candidate = Card.number(CardColor.RED, 9);
        assertTrue(UnoRules.canPlay(candidate, top, CardColor.RED));
    }

    @Test
    void numberCardCanBePlayedByMatchingNumber() {
        Card top = Card.number(CardColor.RED, 5);
        Card candidate = Card.number(CardColor.BLUE, 5);
        assertTrue(UnoRules.canPlay(candidate, top, CardColor.RED));
    }

    @Test
    void actionCardCanBePlayedByMatchingActionType() {
        Card top = Card.action(CardColor.RED, CardType.SKIP);
        Card candidate = Card.action(CardColor.BLUE, CardType.SKIP);
        assertTrue(UnoRules.canPlay(candidate, top, CardColor.RED));
    }

    @Test
    void wildCardsArePlayable() {
        Card top = Card.number(CardColor.GREEN, 7);
        assertTrue(UnoRules.canPlay(Card.wild(), top, CardColor.GREEN));
        assertTrue(UnoRules.canPlay(Card.wildDrawFour(), top, CardColor.GREEN));
    }

    @Test
    void illegalCardIsRejected() {
        Card top = Card.number(CardColor.RED, 5);
        Card candidate = Card.number(CardColor.BLUE, 9);
        assertFalse(UnoRules.canPlay(candidate, top, CardColor.RED));
    }

    @Test
    void selectedWildColorControlsFutureLegalPlay() {
        Card top = Card.wild();
        Card candidate = Card.number(CardColor.GREEN, 3);
        assertTrue(UnoRules.canPlay(candidate, top, CardColor.GREEN));
        assertFalse(UnoRules.canPlay(candidate, top, CardColor.YELLOW));
    }
}
