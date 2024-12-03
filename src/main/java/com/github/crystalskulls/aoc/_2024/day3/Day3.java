package com.github.crystalskulls.aoc._2024.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends Puzzle {

    String corruptedMemory;
    Pattern mulPattern = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)");
    Pattern digitPattern = Pattern.compile("\\d+");
    Pattern instructionPattern = Pattern.compile("(do\\(\\)|don't\\(\\))");

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        Matcher mulMatcher = mulPattern.matcher(corruptedMemory);
        int result = 0;
        while(mulMatcher.find()) {
            result += multipy(mulMatcher);
        }
        System.out.println("Part 1: " + result);
    }

    @Override
    protected void solvePart2() {
        int result = 0;
        Map<Integer, Boolean> instructionMap = new TreeMap<>();
        instructionMap.put(0, true);
        Matcher instructionMatcher = instructionPattern.matcher(corruptedMemory);
        while(instructionMatcher.find()) {
            instructionMap.put(instructionMatcher.start(), instructionMatcher.group().equals("do()"));
        }
        List<Map.Entry<Integer, Boolean>> instructions = new ArrayList<>(instructionMap.entrySet());
        for (int i = 0; i < instructions.size(); i++) {
            Boolean enabled = instructions.get(i).getValue();
            if(enabled) {
                String memorySnippet = corruptedMemory.substring(instructions.get(i).getKey(), instructions.size() - 1 == i ? corruptedMemory.length() : instructions.get(i+1).getKey());
                Matcher mulMatcher = mulPattern.matcher(memorySnippet);
                while(mulMatcher.find()) {
                    result += multipy(mulMatcher);
                }
            }
        }
        System.out.println("Part 2: " + result);
    }

    @Override
    protected void readInputData(String inputFile) {
        corruptedMemory = FileReader.read(inputFile);
    }

    private int multipy(Matcher mulMatcher) {
        String mulToken = mulMatcher.group();
        Matcher digitMatcher = digitPattern.matcher(mulToken);
        digitMatcher.find();
        int i = Integer.parseInt(digitMatcher.group());
        digitMatcher.find();
        int j = Integer.parseInt(digitMatcher.group());
        return i * j;
    }
}
