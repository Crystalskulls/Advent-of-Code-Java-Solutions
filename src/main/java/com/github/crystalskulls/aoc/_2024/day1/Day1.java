package com.github.crystalskulls.aoc._2024.day1;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;

public class Day1 extends Puzzle {

    List<Integer> leftList = new ArrayList<>();
    List<Integer> rightList = new ArrayList<>();

    public Day1() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day1/input.txt";
    }

    @Override
    protected void solvePart1() {
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        System.out.println("Part 1: " + totalDistance);
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + leftList.stream().mapToInt(leftLocation -> leftLocation * rightList.stream().filter(rightLocation -> Objects.equals(rightLocation, leftLocation)).toList().size()).sum());
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        lines.forEach(line -> {
            String[] locationPairs = line.split(" {3}");
            leftList.add(Integer.parseInt(locationPairs[0]));
            rightList.add(Integer.parseInt(locationPairs[1]));
        });
        Collections.sort(leftList);
        Collections.sort(rightList);
    }
}
