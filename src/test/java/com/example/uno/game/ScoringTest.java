package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoringTest {
    @Test
    void numberCardsScoreFaceValue() {
        assertEquals(7, Card.number(CardColor.BLUE, 7).scoreValue());
    }

    @Test
    void coloredActionCardsScoreTwenty() {
        assertEquals(20, Card.action(CardColor.YELLOW, CardType.SKIP).scoreValue());
        assertEquals(20, Card.action(CardColor.YELLOW, CardType.REVERSE).scoreValue());
        assertEquals(20, Card.action(CardColor.YELLOW, CardType.DRAW_TWO).scoreValue());
    }

    @Test
    void wildCardsScoreFifty() {
        assertEquals(50, Card.wild().scoreValue());
        assertEquals(50, Card.wildDrawFour().scoreValue());
    }
}
