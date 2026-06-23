package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DrawPassAndUnoPenaltyTest {
    @Test
    void drawnCardMayBePlayedImmediatelyWhenPolicyAllowsAndCardIsLegal() {
        Card topCard = Card.number(CardColor.RED, 7);
        Card drawnCard = Card.number(CardColor.RED, 2);

        assertTrue(DrawPassRules.mayPlayDrawnCard(
                drawnCard,
                topCard,
                CardColor.RED,
                DrawPassPolicy.DRAW_ONE_THEN_PLAY_IF_LEGAL
        ));
    }

    @Test
    void drawnCardCannotBePlayedWhenPolicyIsDrawAndPass() {
        Card topCard = Card.number(CardColor.RED, 7);
        Card drawnCard = Card.number(CardColor.RED, 2);

        assertFalse(DrawPassRules.mayPlayDrawnCard(
                drawnCard,
                topCard,
                CardColor.RED,
                DrawPassPolicy.DRAW_ONE_AND_PASS
        ));
    }

    @Test
    void drawnCardCannotBePlayedImmediatelyWhenIllegal() {
        Card topCard = Card.number(CardColor.RED, 7);
        Card drawnCard = Card.number(CardColor.BLUE, 2);

        assertFalse(DrawPassRules.mayPlayDrawnCard(
                drawnCard,
                topCard,
                CardColor.RED,
                DrawPassPolicy.DRAW_ONE_THEN_PLAY_IF_LEGAL
        ));
    }

    @Test
    void missedUnoPenaltyAppliesWhenPlayerHasOneCardAndDidNotCallUno() {
        Player player = new Player("Alice", false);
        player.addCard(Card.number(CardColor.RED, 5));

        assertTrue(UnoPenalty.shouldApplyMissedUnoPenalty(player));
    }

    @Test
    void missedUnoPenaltyDoesNotApplyWhenPlayerCalledUno() {
        Player player = new Player("Alice", false);
        player.addCard(Card.number(CardColor.RED, 5));
        player.callUno();

        assertFalse(UnoPenalty.shouldApplyMissedUnoPenalty(player));
    }

    @Test
    void missedUnoPenaltyDoesNotApplyWhenPlayerHasMoreThanOneCard() {
        Player player = new Player("Alice", false);
        player.addCard(Card.number(CardColor.RED, 5));
        player.addCard(Card.number(CardColor.BLUE, 6));

        assertFalse(UnoPenalty.shouldApplyMissedUnoPenalty(player));
    }
}
