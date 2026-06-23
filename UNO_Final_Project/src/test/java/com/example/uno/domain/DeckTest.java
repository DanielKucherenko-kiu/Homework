package com.example.uno.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    @Test
    void standardDeckHas108Cards() {
        assertEquals(108, Deck.standardCards().size());
    }

    @Test
    void standardDeckHasExpectedWildCards() {
        List<Card> cards = Deck.standardCards();
        long wilds = cards.stream().filter(card -> card.getType() == CardType.WILD).count();
        long wildDrawFours = cards.stream().filter(card -> card.getType() == CardType.WILD_DRAW_FOUR).count();
        assertEquals(4, wilds);
        assertEquals(4, wildDrawFours);
    }

    @Test
    void eachColorHasOneZeroAndTwoOfEachOneToNine() {
        List<Card> cards = Deck.standardCards();
        for (CardColor color : List.of(CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE)) {
            long zeros = cards.stream()
                    .filter(card -> card.getType() == CardType.NUMBER)
                    .filter(card -> card.getColor() == color)
                    .filter(card -> card.getNumber() == 0)
                    .count();
            assertEquals(1, zeros);
            for (int number = 1; number <= 9; number++) {
                int n = number;
                long count = cards.stream()
                        .filter(card -> card.getType() == CardType.NUMBER)
                        .filter(card -> card.getColor() == color)
                        .filter(card -> card.getNumber() == n)
                        .count();
                assertEquals(2, count);
            }
        }
    }
}
