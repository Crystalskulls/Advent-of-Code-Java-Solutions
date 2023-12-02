package com.github.crystalskulls.aoc._2015.day2;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day2 extends Puzzle {

    List<List<Integer>> listOfDimensions;

    private record Dimension(int l, int w, int h) {

    }

    public Day2() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2015/day2/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + this.listOfDimensions.stream().map(dimension -> {
            int l = dimension.get(0), w = dimension.get(1), h = dimension.get(2);
            int areaOfSmallestSide = Stream.of(l*w, w*h, h*l).sorted().findFirst().get();
            return 2*l*w + 2*w*h + 2*h*l + areaOfSmallestSide;
        }).reduce(0, Integer::sum));
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + this.listOfDimensions.stream().map(dimension -> {
            int l = dimension.get(0), w = dimension.get(1), h = dimension.get(2);
            List<Integer> sortedDimension = dimension.stream().sorted().toList();
            int shortestDistance = sortedDimension.get(0) * 2 + sortedDimension.get(1) * 2;
            return l*w*h + shortestDistance;
        }).reduce(0, Integer::sum));
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> input = FileReader.readAllLines(inputFile);
        this.listOfDimensions = input.stream().map(
                dimension -> Arrays.stream(dimension.split("x")).mapToInt(Integer::valueOf).boxed().toList()
        ).toList();
    }
}
