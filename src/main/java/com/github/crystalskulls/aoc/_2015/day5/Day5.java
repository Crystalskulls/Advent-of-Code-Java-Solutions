package com.github.crystalskulls.aoc._2015.day5;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 extends Puzzle {

    private List<String> strings;
    private final Set<String> disallowedSubstrings = Set.of("ab", "cd", "pq", "xy");
    private final Set<String> vowels = Set.of("a", "e", "i", "o", "u");

    public Day5() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2015/day5/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + strings.stream()
                .filter(s -> !hasDisallowedSubstring(s))
                .filter(this::containsAtLeastThreeVowels)
                .filter(this::containsAtLeastOneLetterThatAppearsTwice)
                .count());
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + strings.stream()
                .filter(this::containsPairThatAppearsAtLeastTwice)
                .filter(this::containsAtLeastOneLetterWithOneLetterBetweenThen)
                .count());
    }

    private boolean hasDisallowedSubstring(String s) {
        return disallowedSubstrings.stream().anyMatch(s::contains);
    }

    private boolean containsAtLeastThreeVowels(String s) {
        return Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> vowels.contains(e.getKey()))
                .mapToLong(Map.Entry::getValue)
                .sum() >= 3;
    }

    private boolean containsPairThatAppearsAtLeastTwice(String s) {
        for (int i = 0; i < s.split("").length - 2; i++) {
            String pair = s.substring(i, i + 2);
            if(s.indexOf(pair, i + 2) != -1) {
                return true;
            }
        }
        return false;
    }

    private boolean containsAtLeastOneLetterWithOneLetterBetweenThen(String s) {
        for (int i = 0; i < s.split("").length - 2; i++) {
            if (s.charAt(i) == s.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsAtLeastOneLetterThatAppearsTwice(String s) {
        for (int i = 0; i < s.split("").length - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void readInputData(String inputFile) {
        strings = FileReader.readAllLines(inputFile);
    }
}