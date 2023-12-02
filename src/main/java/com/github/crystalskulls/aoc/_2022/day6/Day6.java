package com.github.crystalskulls.aoc._2022.day6;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day6 extends Puzzle {

    private char[] datastreamBuffer;

    public Day6() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2022/day6/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + findMarkerIndex(4));
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + findMarkerIndex(14));
    }

    private int findMarkerIndex(int distinctCharacters) {
        int marker = -1;
        List<Character> characterMarkerList = new ArrayList<>();
        for (int i = 0; i < this.datastreamBuffer.length; i++) {
            if(i > distinctCharacters-1) {
                characterMarkerList.remove(0);
            }
            characterMarkerList.add(this.datastreamBuffer[i]);
            if(new HashSet<>(characterMarkerList).size() == distinctCharacters) {
                marker = i+1;
                break;
            }
        }
        return marker;
    }

    @Override
    protected void readInputData(String inputFile) {
        this.datastreamBuffer = FileReader.readAllChars(inputFile);
    }
}
