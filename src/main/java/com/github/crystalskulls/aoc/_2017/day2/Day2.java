package com.github.crystalskulls.aoc._2017.day2;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2 extends Puzzle {

    private final List<Row> spreadsheet = new ArrayList<>();

    public Day2() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2017/day2/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + spreadsheet
                .stream()
                .mapToInt(row -> {
                    Collections.sort(row.numbers);
                    return Math.abs(row.numbers.getFirst() - row.numbers.getLast());
                }).sum());
    }

    @Override
    protected void solvePart2() {
        int result = 0;
        for (Row row : spreadsheet) {
            List<Integer> numbers = row.getNumbers();
            for (int i = numbers.size() - 1; i >= 0; i--) {
                for (int j = i-1; j >= 0; j--) {
                    if(numbers.get(i) % numbers.get(j) == 0) {
                        result += numbers.get(i) / numbers.get(j);
                    }
                }
            }
        }
        System.out.println("Part 2: " + result);
    }

    @Override
    protected void readInputData(String inputFile) {
        FileReader.readAllLines(inputFile)
                .stream()
                .map(line -> Arrays.stream(line.split("\\s")).map(Integer::parseInt).toList())
                .forEach(numbers -> spreadsheet.add(new Row(new ArrayList<>(numbers))));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Row {
        List<Integer> numbers;
    }
}
