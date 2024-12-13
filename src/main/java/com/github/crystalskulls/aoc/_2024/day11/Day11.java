package com.github.crystalskulls.aoc._2024.day11;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day11 extends Puzzle {

    Map<Long, Long> stoneMap = new HashMap<>();

    public Day11() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day11/input.txt";
    }

    @Override
    protected void solvePart1() {
        for (int i = 0; i < 25; i++) {
            stoneMap = blink(stoneMap);
        }
        System.out.println("Part 1: " + sumStones());
    }

    @Override
    protected void solvePart2() {
        stoneMap = new HashMap<>();
        readInputData(this.inputFile);
        for (int i = 0; i < 75; i++) {
            stoneMap = blink(stoneMap);
        }
        System.out.println("Part 2: " + sumStones());
    }

    private long sumStones() {
        return stoneMap.values().stream().mapToLong(i -> i).sum();
    }

    private Map<Long, Long> blink(Map<Long, Long> currentStoneMap) {
        Map<Long, Long> newStoneMap = new HashMap<>();
        replaceStonesWithZeroEngraving(currentStoneMap, newStoneMap);
        for (Long engraving : currentStoneMap.keySet()) {
            if(String.valueOf(engraving).length() % 2 == 0) {
                replaceStonesWithEvenNumberOfDigits(currentStoneMap, engraving, newStoneMap);
            } else {
                replaceOtherStonesMultipliedBy2024(currentStoneMap, engraving, newStoneMap);
            }
        }
        return newStoneMap;
    }

    private static void replaceStonesWithZeroEngraving(Map<Long, Long> stoneMap, Map<Long, Long> newStoneMap) {
        Long zeroStones = stoneMap.get(0L);
        stoneMap.remove(0L);
        if (zeroStones != null) {
            newStoneMap.put(1L, zeroStones);
        }
    }

    private static void replaceStonesWithEvenNumberOfDigits(Map<Long, Long> currentStoneMap, Long engraving, Map<Long, Long> newStoneMap) {
        String engravingAsString = String.valueOf(engraving);
        Long numberOfStones = currentStoneMap.get(engraving);
        String leftNewEngraving = engravingAsString.substring(0, engravingAsString.length() / 2);
        String rightNewEngraving = engravingAsString.substring(engravingAsString.length() / 2);
        newStoneMap.merge(Long.parseLong(leftNewEngraving), numberOfStones, Long::sum);
        newStoneMap.merge(Long.parseLong(rightNewEngraving), numberOfStones, Long::sum);
    }

    private static void replaceOtherStonesMultipliedBy2024(Map<Long, Long> currentStoneMap, Long engraving, Map<Long, Long> newStoneMap) {
        Long newEngraving = engraving * 2024;
        Long numberOfStones = currentStoneMap.get(engraving);
        newStoneMap.merge(newEngraving, numberOfStones, Long::sum);
    }

    @Override
    protected void readInputData(String inputFile) {
        Arrays.stream(FileReader.read(inputFile).split(" ")).mapToLong(Long::parseLong).forEach(v -> stoneMap.merge(v, 1L, Long::sum));
    }
}