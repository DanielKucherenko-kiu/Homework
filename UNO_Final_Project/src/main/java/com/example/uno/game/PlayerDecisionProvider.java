package com.example.uno.game;

import com.example.uno.domain.Card;
import com.example.uno.domain.CardColor;
import com.example.uno.domain.Player;

import java.util.List;

public interface PlayerDecisionProvider {
    Card chooseCardToPlay(Player player, Card topCard, CardColor activeColor, List<Card> legalCards);

    boolean shouldPlayDrawnCard(Player player, Card drawnCard, Card topCard, CardColor activeColor);

    CardColor chooseColor(Player player, Card wildCard);

    boolean shouldCallUno(Player player);
}
