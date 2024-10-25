package com.github.crystalskulls.aoc._2023.day15;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.github.crystalskulls.aoc.common.Regex;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 extends Puzzle {

    private List<String> steps;
    private List<String> labels;
    private Map<Integer, List<String>> boxes = new HashMap<>();

    public Day15() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day15/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + this.hashAlgorithm(this.steps).stream().reduce(0, Integer::sum));
    }

    @Override
    protected void solvePart2() {
        List<Integer> boxKeys = this.hashAlgorithm(this.labels);
        for (int i = 0; i < boxKeys.size(); i++) {
            List<String> lenses = this.boxes.get(boxKeys.get(i));
            if(lenses == null) {
                lenses = new ArrayList<>();
            }
            lenses.add(this.labels.get(i));
            this.boxes.put(boxKeys.get(i), lenses);
        }
        System.out.println(this.boxes);
    }

    @Override
    protected void readInputData(String inputFile) {
        this.steps = FileReader.readAllLines(inputFile, ",");
        this.labels = Regex.findLensLabels(FileReader.read(inputFile));
    }

    private List<Integer> hashAlgorithm(List<String> strings) {
        return strings.stream().map(string -> {
            int sum = 0;
            for (byte asciiCode : string.getBytes(StandardCharsets.US_ASCII)) {
                sum += asciiCode;
                sum *= 17;
                sum %= 256;
            }
            return sum;
        }).toList();
    }
}
