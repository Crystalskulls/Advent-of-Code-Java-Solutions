package com.github.crystalskulls.aoc._2022.day2;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.List;
import java.util.Map;

public class Day2 extends Puzzle {

    private List<String> strategyGuide;
    private final Map<String, Integer> selectedShapMap = Map.of(
            "A X", 4,
            "A Y", 8,
            "A Z", 3,
            "B X", 1,
            "B Y", 5,
            "B Z", 9,
            "C X", 7,
            "C Y", 2,
            "C Z", 6
    );

    private final Map<String, Integer> shapeToChooseMap = Map.of(
            "A X", 3,
            "A Y", 4,
            "A Z", 8,
            "B X", 1,
            "B Y", 5,
            "B Z", 9,
            "C X", 2,
            "C Y", 6,
            "C Z", 7
    );

    public Day2() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2022/day2/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + this.playRockPaperScissor(this.selectedShapMap));
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + this.playRockPaperScissor(this.shapeToChooseMap));
    }

    private int playRockPaperScissor(Map<String, Integer> scoreMap) {
        return this.strategyGuide
                .stream().map(scoreMap::get)
                .reduce(0, Integer::sum);
    }

    @Override
    protected void readInputData(String inputFile) {
        this.strategyGuide = FileReader.readAllLines(inputFile);
    }
}
