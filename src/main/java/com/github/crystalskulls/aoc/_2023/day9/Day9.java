package com.github.crystalskulls.aoc._2023.day9;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.github.crystalskulls.aoc.common.Regex;

import java.util.ArrayList;
import java.util.List;

public class Day9 extends Puzzle {

    private final List<History> oasisReport = new ArrayList<>();

    public Day9() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day9/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + this.oasisReport.stream().map(History::predictNextValue).reduce(0, Integer::sum));
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + this.oasisReport.stream().map(History::predictPreviousValue).reduce(0, Integer::sum));
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        lines.forEach(line -> {
            this.oasisReport.add(new History(Regex.findNumbers(line, Regex.positiveAndNegativeNumberPattern, Integer::parseInt)));
        });
    }
}
