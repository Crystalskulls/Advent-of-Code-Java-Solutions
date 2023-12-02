package com.github.crystalskulls.aoc._2015.day1;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.lang3.StringUtils;

public class Day1 extends Puzzle {

    private String floorInstructions;

    public Day1() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2015/day1/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + (StringUtils.countMatches(this.floorInstructions, '(') - StringUtils.countMatches(this.floorInstructions, ')')));
    }

    @Override
    protected void solvePart2() {
        int currentFloor = 0;
        char[] floorInstructions = this.floorInstructions.toCharArray();
        for (int i = 0; i < floorInstructions.length; i++) {
            currentFloor += floorInstructions[i] == '(' ? 1 : -1;
            if(currentFloor == -1) {
                System.out.println("Part 2: " + (i + 1));
                break;
            }
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        this.floorInstructions = FileReader.read(inputFile);
    }
}
