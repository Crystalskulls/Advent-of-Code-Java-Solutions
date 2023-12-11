package com.github.crystalskulls.aoc._2023.day11;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.List;

public class Day11 extends Puzzle {

    private Universe universe;

    public Day11() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day11/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + this.universe.exploreGalaxies());
    }

    @Override
    protected void solvePart2() {
        this.universe = new Universe(999999L);
        List<String> rows = FileReader.readAllLines(inputFile);
        for (String row : rows) {
            this.universe.getImage().add(row.chars().mapToObj(c -> (char) c).toList());
        }
        this.universe.createNodeMap();
        this.universe.createNodeSystem();
        this.universe.expand();
        this.universe.createGraph();
        System.out.println("Part 2: " + this.universe.exploreGalaxies());
    }

    @Override
    protected void readInputData(String inputFile) {
        this.universe = new Universe(1);
        List<String> rows = FileReader.readAllLines(inputFile);
        for (String row : rows) {
            this.universe.getImage().add(row.chars().mapToObj(c -> (char) c).toList());
        }
        this.universe.createNodeMap();
        this.universe.createNodeSystem();
        this.universe.expand();
        this.universe.createGraph();
    }
}
