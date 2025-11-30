package com.github.crystalskulls.aoc._2018.day5;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day5 extends Puzzle {

    private String polymer;

    public Day5() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2018/day5/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("\nPart 1: " + collapsePolymer2(polymer));
    }

    private int collapsePolymer2(String polymer) {
        StringBuilder collapsedPolymer = new StringBuilder();
        for (char currentUnit : polymer.toCharArray()) {
            int size = collapsedPolymer.length();
            if(size > 0) {
                char previousUnit = collapsedPolymer.charAt(size - 1);
                if(Character.toLowerCase(previousUnit) == Character.toLowerCase(currentUnit) && (Character.isLowerCase(previousUnit) != Character.isLowerCase(currentUnit))) {
                    collapsedPolymer.deleteCharAt(size - 1);
                    continue;
                }
            }
            collapsedPolymer.append(currentUnit);

        }
        return collapsedPolymer.length();
    }

    @Override
    protected void solvePart2() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Integer min = Integer.MAX_VALUE;
        for (char c : alphabet) {
            readInputData(inputFile);
            polymer = polymer.replaceAll("(?i)" + c, "");
            int r = collapsePolymer2(polymer);
            if (r < min) {
                min = r;
            }
        }
        System.out.println("Part 2: " + min);
    }

    @Override
    protected void readInputData(String inputFile) {
        polymer = FileReader.read(inputFile);
    }
}
