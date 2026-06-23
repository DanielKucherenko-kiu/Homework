package com.example.uno.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Deck {
    private final List<Card> cards;
    private final Random random;

    public Deck(List<Card> cards) {
        this(cards, new Random());
    }

    public Deck(List<Card> cards, Random random) {
        this.cards = new ArrayList<>(cards);
        this.random = random;
    }

    public static Deck standardShuffled() {
        Deck deck = new Deck(standardCards());
        deck.shuffle();
        return deck;
    }

    public static Deck standardShuffled(Random random) {
        Deck deck = new Deck(standardCards(), random);
        deck.shuffle();
        return deck;
    }

    public static List<Card> standardCards() {
        List<Card> cards = new ArrayList<>();
        for (CardColor color : List.of(CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE)) {
            cards.add(Card.number(color, 0));
            for (int number = 1; number <= 9; number++) {
                cards.add(Card.number(color, number));
                cards.add(Card.number(color, number));
            }
            for (int i = 0; i < 2; i++) {
                cards.add(Card.action(color, CardType.SKIP));
                cards.add(Card.action(color, CardType.REVERSE));
                cards.add(Card.action(color, CardType.DRAW_TWO));
            }
        }
        for (int i = 0; i < 4; i++) {
            cards.add(Card.wild());
            cards.add(Card.wildDrawFour());
        }
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("Deck is empty.");
        }
        return cards.remove(cards.size() - 1);
    }

    public void addAllAndShuffle(List<Card> newCards) {
        cards.addAll(newCards);
        shuffle();
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
