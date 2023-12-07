package com.github.crystalskulls.aoc._2023.day7;

import java.util.Comparator;

public class HandComparator implements Comparator<Hand> {

    @Override
    public int compare(Hand hand, Hand otherHand) {
        int comparedHandValue =  hand.getType().compareTo(otherHand.getType());
        if(comparedHandValue == 0) {
            for (int i = 0; i < 5; i++) {
                int comparedCardValue = hand.getCards().get(i).compareTo(otherHand.getCards().get(i));
                if (comparedCardValue == 0) {
                    continue;
                }
                return comparedCardValue;
            }
        }
        return comparedHandValue;
    }
}
