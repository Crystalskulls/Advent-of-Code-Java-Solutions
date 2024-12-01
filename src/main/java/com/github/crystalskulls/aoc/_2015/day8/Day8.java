package com.github.crystalskulls.aoc._2015.day8;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.List;

public class Day8 extends Puzzle {

    private List<String> stringLiterals;
    private int numberOfCharactersInCode;

    public Day8() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2015/day8/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + (calculateNumberOfCharactersOfCode() - calculateNumberOfCharactersInMemory()));
    }

    @Override
    protected void solvePart2() {
        int numberOfCharactersOfNewlyEncodedStrings = stringLiterals.stream()
                .map(s -> s.replaceAll("\\\\x..", "§§§§§"))
                .map(s ->
                    s.chars().mapToObj(c -> (char)c).toList()
                )
                        .mapToInt(characterList -> {
                            int i = 2;
                            for (Character character : characterList) {
                                if (character == '"' || character == '\\') {
                                    i += 2;
                                } else {
                                    i++;
                                }
                            }
                            return i;
                        })
                                .sum();
        System.out.println("Part 2: " + (numberOfCharactersOfNewlyEncodedStrings - numberOfCharactersInCode));
    }

    @Override
    protected void readInputData(String inputFile) {
        stringLiterals = FileReader.readAllLines(inputFile);
    }

    private int calculateNumberOfCharactersOfCode() {
        numberOfCharactersInCode = stringLiterals.stream().mapToInt(String::length).sum();
        return numberOfCharactersInCode;
    }

    private int calculateNumberOfCharactersInMemory() {
        return stringLiterals.stream()
                .map(s -> s.replace("\\\\", "$"))
                .map(s -> s.replace("\\\"", "%"))
                .map(s -> s.replaceAll("\\\\x..", "§"))
                .mapToInt(s -> s.length() - 2)
                .sum();
    }
}