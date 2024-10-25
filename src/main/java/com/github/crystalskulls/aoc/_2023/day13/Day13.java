package com.github.crystalskulls.aoc._2023.day13;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 extends Puzzle {

    private final List<Pattern> patternList = new ArrayList<>();

    public Day13() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day13/input.txt";
    }

    @Override
    protected void solvePart1() {
        int numberOfColumns = 0;
        int numberOfRows = 0;
        for (Pattern pattern : this.patternList) {
            int verticalReflection = pattern.findVerticalReflection();
            numberOfColumns += verticalReflection == -1 ? 0 : verticalReflection;
            int horizontalReflection = pattern.findHorizontalReflection();
            numberOfRows += horizontalReflection == -1 ? 0 : horizontalReflection;
        }
        System.out.println("Part 1: " + (numberOfColumns + (100 * numberOfRows)));
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> patternStringList = FileReader.readAllLines(inputFile, "\n\n");
        for (String patternString : patternStringList) {
            Pattern pattern = new Pattern(patternString);
            this.patternList.add(pattern);
        }
    }
}
