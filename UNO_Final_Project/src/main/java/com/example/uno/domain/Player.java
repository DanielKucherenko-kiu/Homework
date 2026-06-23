package com.example.uno.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private final boolean bot;
    private final List<Card> hand = new ArrayList<>();
    private int totalScore;
    private boolean unoCalled;

    public Player(String name, boolean bot) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank.");
        }
        this.name = name.trim();
        this.bot = bot;
    }

    public String getName() {
        return name;
    }

    public boolean isBot() {
        return bot;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void addCard(Card card) {
        hand.add(Objects.requireNonNull(card, "card"));
        if (hand.size() != 1) {
            unoCalled = false;
        }
    }

    public void addCards(List<Card> cards) {
        cards.forEach(this::addCard);
    }

    public void removeCard(Card card) {
        if (!hand.remove(card)) {
            throw new IllegalArgumentException("Card is not in player's hand: " + card);
        }
        if (hand.size() != 1) {
            unoCalled = false;
        }
    }

    public void clearHand() {
        hand.clear();
        unoCalled = false;
    }

    public int handSize() {
        return hand.size();
    }

    public int handScore() {
        return hand.stream().mapToInt(Card::scoreValue).sum();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void addScore(int points) {
        totalScore += points;
    }

    public boolean hasCalledUno() {
        return unoCalled;
    }

    public void callUno() {
        unoCalled = true;
    }

    @Override
    public String toString() {
        return name + (bot ? " [bot]" : "");
    }
}
