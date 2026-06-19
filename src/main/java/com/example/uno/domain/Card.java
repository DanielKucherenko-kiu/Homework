package com.example.uno.domain;

import java.util.Objects;

public final class Card {
    private final CardColor color;
    private final CardType type;
    private final int number;

    private Card(CardColor color, CardType type, int number) {
        this.color = Objects.requireNonNull(color, "color");
        this.type = Objects.requireNonNull(type, "type");
        this.number = number;
    }

    public static Card number(CardColor color, int number) {
        if (!color.isPlayableColor()) {
            throw new IllegalArgumentException("Number cards must have a normal color.");
        }
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("UNO number must be between 0 and 9.");
        }
        return new Card(color, CardType.NUMBER, number);
    }

    public static Card action(CardColor color, CardType type) {
        if (!color.isPlayableColor()) {
            throw new IllegalArgumentException("Action cards must have a normal color.");
        }
        if (type == CardType.NUMBER || type == CardType.WILD || type == CardType.WILD_DRAW_FOUR) {
            throw new IllegalArgumentException("Invalid colored action type: " + type);
        }
        return new Card(color, type, -1);
    }

    public static Card wild() {
        return new Card(CardColor.WILD, CardType.WILD, -1);
    }

    public static Card wildDrawFour() {
        return new Card(CardColor.WILD, CardType.WILD_DRAW_FOUR, -1);
    }

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public boolean isWild() {
        return type == CardType.WILD || type == CardType.WILD_DRAW_FOUR;
    }

    public int scoreValue() {
        return switch (type) {
            case NUMBER -> number;
            case SKIP, REVERSE, DRAW_TWO -> 20;
            case WILD, WILD_DRAW_FOUR -> 50;
        };
    }

    public String displayName() {
        if (type == CardType.NUMBER) {
            return color + " " + number;
        }
        if (type == CardType.WILD) {
            return "WILD";
        }
        if (type == CardType.WILD_DRAW_FOUR) {
            return "WILD DRAW FOUR";
        }
        return color + " " + type.toString().replace('_', ' ');
    }

    @Override
    public String toString() {
        return displayName();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Card card)) return false;
        return number == card.number && color == card.color && type == card.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type, number);
    }
}
