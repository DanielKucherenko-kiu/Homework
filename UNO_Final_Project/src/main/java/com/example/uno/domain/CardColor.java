package com.example.uno.domain;

import java.util.Locale;

public enum CardColor {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    WILD;

    public static CardColor fromInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Color cannot be empty.");
        }
        return CardColor.valueOf(input.trim().toUpperCase(Locale.ROOT));
    }

    public boolean isPlayableColor() {
        return this != WILD;
    }
}
