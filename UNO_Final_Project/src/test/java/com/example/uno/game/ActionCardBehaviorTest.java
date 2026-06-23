package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionCardBehaviorTest {
    @Test
    void skipMakesNextPlayerLoseTurn() {
        Card skip = Card.action(CardColor.RED, CardType.SKIP);

        TurnEffect effect = TurnEngine.resolvePlayedCard(skip, 0, 1, 4);

        assertEquals(2, effect.nextPlayerIndex());
        assertEquals(1, effect.affectedPlayerIndex());
        assertTrue(effect.affectedPlayerLosesTurn());
        assertEquals(0, effect.cardsToDraw());
    }

    @Test
    void reverseChangesDirectionInThreeOrMorePlayerGame() {
        Card reverse = Card.action(CardColor.BLUE, CardType.REVERSE);

        TurnEffect effect = TurnEngine.resolvePlayedCard(reverse, 0, 1, 4);

        assertEquals(-1, effect.newDirection());
        assertEquals(3, effect.nextPlayerIndex());
        assertFalse(effect.affectedPlayerLosesTurn());
    }

    @Test
    void reverseActsLikeSkipInTwoPlayerGame() {
        Card reverse = Card.action(CardColor.YELLOW, CardType.REVERSE);

        TurnEffect effect = TurnEngine.resolvePlayedCard(reverse, 0, 1, 2);

        assertEquals(0, effect.nextPlayerIndex());
        assertEquals(1, effect.affectedPlayerIndex());
        assertTrue(effect.affectedPlayerLosesTurn());
    }

    @Test
    void drawTwoMakesNextPlayerDrawTwoAndLoseTurn() {
        Card drawTwo = Card.action(CardColor.GREEN, CardType.DRAW_TWO);

        TurnEffect effect = TurnEngine.resolvePlayedCard(drawTwo, 0, 1, 4);

        assertEquals(2, effect.nextPlayerIndex());
        assertEquals(1, effect.affectedPlayerIndex());
        assertEquals(2, effect.cardsToDraw());
        assertTrue(effect.affectedPlayerLosesTurn());
    }

    @Test
    void wildDrawFourMakesNextPlayerDrawFourAndLoseTurn() {
        Card wildDrawFour = Card.wildDrawFour();

        TurnEffect effect = TurnEngine.resolvePlayedCard(wildDrawFour, 0, 1, 4);

        assertEquals(2, effect.nextPlayerIndex());
        assertEquals(1, effect.affectedPlayerIndex());
        assertEquals(4, effect.cardsToDraw());
        assertTrue(effect.affectedPlayerLosesTurn());
    }
}
