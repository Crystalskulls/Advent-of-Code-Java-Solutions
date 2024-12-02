package com.github.crystalskulls.aoc._2024.day2;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;

public class Day2 extends Puzzle {

    List<Report> reports = new ArrayList<>();

    public Day2() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day2/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + reports.stream().filter(report -> report.isSafe(false)).toList().size());
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + reports.stream().filter(report -> report.isSafe(true)).toList().size());
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        lines.forEach(line -> reports.add(new Report(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList())));
    }

    static class Report {

        List<Integer> levels;

        Report(List<Integer> levels) {
            this.levels = levels;
        }

        boolean isSafe(boolean activeProblemDampener) {
            if(!activeProblemDampener) {
                return observeRules(this.levels);
            } else {
                int skipIndex = 0;
                while (skipIndex < this.levels.size()) {
                    List<Integer> levels = new ArrayList<>(this.levels);
                    levels.remove(skipIndex);
                    if(observeRules(levels)) {
                        return true;
                    }
                    skipIndex++;
                }
                return false;
            }
        }

        private static boolean observeRules(List<Integer> levels) {
            boolean increasing = levels.get(1) > levels.get(0);
            for (int i = 1; i < levels.size(); i++) {
                int lastLevel = levels.get(i-1);
                int currentLevel = levels.get(i);
                int diff = Math.abs(lastLevel - currentLevel);
                if((diff < 1 || diff > 3) ||
                        (increasing && currentLevel < lastLevel) ||
                        (!increasing && currentLevel > lastLevel)
                ) {
                    return false;
                }
            }
            return true;
        }
    }
}
