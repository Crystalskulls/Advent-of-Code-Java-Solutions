package com.github.crystalskulls.aoc._2016.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;

public class Day3 extends Puzzle {

    List<List<Integer>> triangles = new ArrayList<>();
    List<List<Integer>> verticalTriangles = new ArrayList<>();

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2016/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + triangles.stream().map(sides -> sides.getFirst() + sides.get(1) > sides.getLast()).filter(isValidTriangle -> isValidTriangle).count());
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + verticalTriangles.stream().map(sides -> sides.getFirst() + sides.get(1) > sides.getLast()).filter(isValidTriangle -> isValidTriangle).count());
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        List<Integer> firstVerticalTriangleSides = new ArrayList<>();
        List<Integer> secondVerticalTriangleSides = new ArrayList<>();
        List<Integer> thirdVerticalTriangleSides = new ArrayList<>();
        for (String line : lines) {
            List<Integer> sides = new ArrayList<>();
            for (String side : line.trim().split("\\s+")) {
                sides.add(Integer.valueOf(side));
            }
            extractVerticalTriangleSides(firstVerticalTriangleSides, sides, secondVerticalTriangleSides, thirdVerticalTriangleSides);
            addAndSortTriangleSides(sides);
            if(firstVerticalTriangleSides.size() == 3) {
                sortAndAddVerticalTriangleSides(firstVerticalTriangleSides, secondVerticalTriangleSides, thirdVerticalTriangleSides);
                firstVerticalTriangleSides = new ArrayList<>();
                secondVerticalTriangleSides = new ArrayList<>();
                thirdVerticalTriangleSides = new ArrayList<>();
            }
        }
    }

    private void addAndSortTriangleSides(List<Integer> sides) {
        Collections.sort(sides);
        triangles.add(sides);
    }

    private static void extractVerticalTriangleSides(List<Integer> firstVerticalTriangleSides, List<Integer> sides, List<Integer> secondVerticalTriangleSides, List<Integer> thirdVerticalTriangleSides) {
        firstVerticalTriangleSides.add(sides.getFirst());
        secondVerticalTriangleSides.add(sides.get(1));
        thirdVerticalTriangleSides.add(sides.getLast());
    }

    private void sortAndAddVerticalTriangleSides(List<Integer> firstVerticalTriangleSides, List<Integer> secondVerticalTriangleSides, List<Integer> thirdVerticalTriangleSides) {
        Collections.sort(firstVerticalTriangleSides);
        Collections.sort(secondVerticalTriangleSides);
        Collections.sort(thirdVerticalTriangleSides);
        verticalTriangles.add(firstVerticalTriangleSides);
        verticalTriangles.add(secondVerticalTriangleSides);
        verticalTriangles.add(thirdVerticalTriangleSides);
    }
}
