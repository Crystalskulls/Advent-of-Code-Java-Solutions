package com.github.crystalskulls.aoc._2024.day19;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.google.common.collect.Sets;

import java.util.*;

public class Day19 extends Puzzle {

    List<String> towelPatterns;
    List<String> designs = new ArrayList<>();

    public Day19() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day19/input.txt";
    }

    @Override
    protected void solvePart1() {
        int valid = 0;
        System.out.println(towelPatterns);
        System.out.println(designs);
        for (String design : designs) {
            List<Set<String>> towelPatternCombinations = new ArrayList<>();
            for (int i = 0; i < design.length(); i++) {
                towelPatternCombinations.add(Set.copyOf(towelPatterns.subList(0, i+1)));
            }
            Set<List<String>> cartesianProduct = Sets.cartesianProduct(towelPatternCombinations);
            System.out.println(cartesianProduct);
            break;
        }
        System.out.println("Part 1: " + valid);
    }

    @Override
    protected void solvePart2() {
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> input = FileReader.readAllLines(inputFile);
        towelPatterns = Arrays.stream(input.getFirst().split(", ")).toList();
        for (int i = 2; i < input.size(); i++) {
            designs.add(input.get(i));
        }
    }


}