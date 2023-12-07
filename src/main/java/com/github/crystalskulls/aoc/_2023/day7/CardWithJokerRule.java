package com.github.crystalskulls.aoc._2023.day7;

import java.util.HashMap;
import java.util.Map;

public enum CardWithJokerRule {

    JOKER('J'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'),
    SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'),
    QUEEN('Q'), KING('K'), ACE('A');
    private final char strength;
    private static final Map<Character, CardWithJokerRule> BY_STRENGTH = new HashMap<>();

    static {
        for (CardWithJokerRule cardWithJokerRule : values()) {
            BY_STRENGTH.put(cardWithJokerRule.strength, cardWithJokerRule);
        }
    }

    CardWithJokerRule(char strength) {
        this.strength = strength;
    }

    public static CardWithJokerRule fromStrength(char strength) {
        return BY_STRENGTH.get(strength);
    }
}
