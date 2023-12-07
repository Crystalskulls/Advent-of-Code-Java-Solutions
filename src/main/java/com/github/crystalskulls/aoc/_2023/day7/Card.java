package com.github.crystalskulls.aoc._2023.day7;

import java.util.HashMap;
import java.util.Map;

public enum Card {

    TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'),
    SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'), JACK('J'),
    QUEEN('Q'), KING('K'), ACE('A');
    private final char strength;
    private static final Map<Character, Card> BY_STRENGTH = new HashMap<>();

    static {
        for (Card card: values()) {
            BY_STRENGTH.put(card.strength, card);
        }
    }

    Card(char strength) {
        this.strength = strength;
    }

    public static Card fromStrength(char strength) {
        return BY_STRENGTH.get(strength);
    }
}
