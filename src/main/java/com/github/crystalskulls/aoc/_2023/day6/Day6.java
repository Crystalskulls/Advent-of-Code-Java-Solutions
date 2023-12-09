package com.github.crystalskulls.aoc._2023.day6;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.github.crystalskulls.aoc.common.Regex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day6 extends Puzzle {

    private List<Long> times = new ArrayList<>();
    private List<Long> distances = new ArrayList<>();

    public Day6() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day6/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + calculateWaysToWin(this.times, this.distances));
    }

    @Override
    protected void solvePart2() {
        List<Long> times = List.of(Long.parseLong(this.times.stream().map(Object::toString).collect(Collectors.joining())));
        List<Long> distances = List.of(Long.parseLong(this.distances.stream().map(Object::toString).collect(Collectors.joining())));
        System.out.println("Part 2: " + this.calculateWaysToWin(times, distances));
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        this.times = Regex.findNumbers(lines.getFirst(), Regex.positiveNumberPattern, Long::parseLong);
        this.distances = Regex.findNumbers(lines.getLast(), Regex.positiveNumberPattern, Long::parseLong);
    }

    private int calculateWaysToWin(List<Long> times, List<Long> distances) {
        int result = 1;
        for (int i = 0; i < times.size(); i++) {
            int waysToWin = 0;
            Long time = times.get(i);
            Long distanceRecord = distances.get(i);
            Boolean stillWaysToWin = null;
            for (int j = 0; j < time+1; j++) {
                if(j * (time - j) > distanceRecord) {
                    waysToWin++;
                    stillWaysToWin = false;
                } else if(Boolean.FALSE.equals(stillWaysToWin)) {
                    break;
                }
            }
            result *= waysToWin;
        }
        return result;
    }
}
