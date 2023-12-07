package com.github.crystalskulls.aoc._2023.day7;

import java.util.Comparator;

public class HandComparatorWithJokerRule implements Comparator<Hand> {

    @Override
    public int compare(Hand hand, Hand otherHand) {
        int comparedHandValue = hand.getTypeWithJokerRule().compareTo(otherHand.getTypeWithJokerRule());
        if (comparedHandValue == 0) {
            for (int i = 0; i < 5; i++) {
                int comparedCardValue = hand.getCardsWithJokerRule().get(i).compareTo(otherHand.getCardsWithJokerRule().get(i));
                if (comparedCardValue == 0) {
                    continue;
                }
                return comparedCardValue;
            }
        }
        return comparedHandValue;
    }
}
