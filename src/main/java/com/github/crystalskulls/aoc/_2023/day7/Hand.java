package com.github.crystalskulls.aoc._2023.day7;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@ToString
@Getter
public class Hand{

    private final List<Card> cards;
    private final List<CardWithJokerRule> cardsWithJokerRule;
    private Type type;
    private Type typeWithJokerRule;
    private final Map<Card, Integer> characterCount = new HashMap<>();
    private final int bid;

    public Hand(String camelCards, int bid) {
        this.cards = camelCards.chars().mapToObj(c -> Card.fromStrength((char)c)).toList();
        this.cardsWithJokerRule = camelCards.chars().mapToObj(c -> CardWithJokerRule.fromStrength((char)c)).toList();
        this.bid = bid;
        this.countCharacters();
        this.determineType();
        this.determineTypeWithJokerRule();
    }

    private void countCharacters() {
        for (Card card : this.cards) {
            int count = this.characterCount.get(card) == null ? 0 : this.characterCount.get(card);
            this.characterCount.put(card, count + 1);
        }
    }

    private void determineType() {
        Map<Integer, Type> typeMap = Map.of(
                5, Type.FIVE_OF_A_KIND,
                4, Type.FOUR_OF_A_KIND,
                3, Type.THREE_OF_A_KIND,
                2, Type.ONE_PAIR,
                1, Type.HIGH_CARD
        );
        List<Integer> sameLabelCounts = this.characterCount.values().stream().toList();
        if(new HashSet<>(sameLabelCounts).containsAll(List.of(3, 2))) {
            this.type = Type.FULL_HOUSE;
        } else if(sameLabelCounts.stream().filter(i -> i == 2).toList().size() == 2) {
            this.type = Type.TWO_PAIR;
        } else {
            this.type = typeMap.get(sameLabelCounts.stream().max(Comparator.naturalOrder()).get());
        }
    }

    private void determineTypeWithJokerRule() {
        Map<Integer, Map<Type, Type>> jokerTypeMap = Map.of(
                5, Map.of(Type.FIVE_OF_A_KIND, Type.FIVE_OF_A_KIND),
                4, Map.of(Type.FOUR_OF_A_KIND, Type.FIVE_OF_A_KIND),
                3, Map.of(
                        Type.FULL_HOUSE, Type.FIVE_OF_A_KIND,
                        Type.HIGH_CARD, Type.FOUR_OF_A_KIND,
                        Type.THREE_OF_A_KIND, Type.FOUR_OF_A_KIND
                ),
                2, Map.of(
                        Type.FULL_HOUSE, Type.FIVE_OF_A_KIND,
                        Type.HIGH_CARD, Type.THREE_OF_A_KIND,
                        Type.ONE_PAIR, Type.THREE_OF_A_KIND,
                        Type.TWO_PAIR, Type.FOUR_OF_A_KIND
                ),
                1, Map.of(
                        Type.HIGH_CARD, Type.ONE_PAIR,
                        Type.ONE_PAIR, Type.THREE_OF_A_KIND,
                        Type.TWO_PAIR, Type.FULL_HOUSE,
                        Type.THREE_OF_A_KIND, Type.FOUR_OF_A_KIND,
                        Type.FOUR_OF_A_KIND, Type.FIVE_OF_A_KIND
                )
        );
        int numberOfJokers = this.characterCount.get(Card.JACK) == null ? 0 : this.characterCount.get(Card.JACK);
        this.typeWithJokerRule = numberOfJokers == 0 ? this.type : jokerTypeMap.get(numberOfJokers).get(this.type);
    }
}
