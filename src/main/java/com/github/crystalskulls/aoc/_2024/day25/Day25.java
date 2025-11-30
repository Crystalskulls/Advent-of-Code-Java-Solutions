package com.github.crystalskulls.aoc._2024.day25;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day25 extends Puzzle {

    List<List<Integer>> keys = new ArrayList<>();
    List<List<Integer>> locks = new ArrayList<>();

    public Day25() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day25/input.txt";
    }

    @Override
    protected void solvePart1() {

        int c = 0;
        for (List<Integer> key : keys) {
            for (List<Integer> lock : locks) {

                List<Integer> result = IntStream.range(0, key.size())
                        .mapToObj(i -> key.get(i) + lock.get(i))
                        .toList();
                if(result.stream().noneMatch(i -> i >= 6)) {
                    c++;
                }

            }
        }
        System.out.println("Part 1: " + c);
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> keysAndLocks = FileReader.readAllLines(inputFile, "\n\n");
        for (String keysAndLock : keysAndLocks) {
            String[] rows = keysAndLock.split("\n");
            if(rows[0].startsWith(".")) {
                parseKey(rows);
            } else {
                parseLock(rows);
            }
        }
    }

    private void parseKey(String[] rows) {
        List<Integer> key = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int c = 0;
            for (int j = rows.length - 2; j >= 0; j--) {
                if(rows[j].toCharArray()[i] == '#') {
                    c++;
                }
            }
            key.add(c);
        }
        keys.add(key);
    }

    private void parseLock(String[] rows) {
        List<Integer> lock = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int c = 0;
            for (int j = 1; j < rows.length; j++) {
                if(rows[j].toCharArray()[i] == '#') {
                    c++;
                }
            }
            lock.add(c);
        }
        locks.add(lock);
    }

}