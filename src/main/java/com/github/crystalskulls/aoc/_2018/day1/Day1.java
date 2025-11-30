package com.github.crystalskulls.aoc._2018.day1;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 extends Puzzle {

    List<String> frequencies;
    Map<Integer, Integer> frequenciesMap = new HashMap<>();

    public Day1() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2018/day1/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + frequencies.stream().mapToInt(Integer::parseInt).sum());
    }

    @Override
    protected void solvePart2() {
        int currentFrequency = 0;
        frequenciesMap.put(0, null);
        while(true) {
            for (String frequency : frequencies) {
                int nextFrequency = currentFrequency + Integer.parseInt(frequency);
                if(frequenciesMap.containsKey(nextFrequency)) {
                    System.out.println("Part 2: " + nextFrequency);
                    System.exit(0);
                }
                frequenciesMap.put(nextFrequency, null);
                currentFrequency = nextFrequency;
            }
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        frequencies = FileReader.readAllLines(this.inputFile);
    }
}
