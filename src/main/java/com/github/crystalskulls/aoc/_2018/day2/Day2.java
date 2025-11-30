package com.github.crystalskulls.aoc._2018.day2;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2 extends Puzzle {

    List<String> boxIds = new ArrayList<>();

    public Day2() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2018/day2/input.txt";
    }

    @Override
    protected void solvePart1() {
        int twoTimes = 0;
        int threeTimes = 0;
        for (String boxId : boxIds) {
            Map<String, Long> charCount = boxId.codePoints()
                    .mapToObj(Character::toString)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            twoTimes += charCount.containsValue(2L) ? 1 : 0;
            threeTimes += charCount.containsValue(3L) ? 1 : 0;
        }
        System.out.println("Part 1: " + twoTimes * threeTimes);
    }

    @Override
    protected void solvePart2() {
        for (int i = 0; i < boxIds.getFirst().length(); i++) {
            int finalI = i;
            Map<String, Long> slicedBoxIds = boxIds.stream().map(boxId -> {
                StringBuilder sb = new StringBuilder(boxId);
                sb.deleteCharAt(finalI);
                return sb.toString();
            }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            if(slicedBoxIds.containsValue(2L)) {
                for (String boxId : slicedBoxIds.keySet()) {
                    if(slicedBoxIds.get(boxId) == 2L) {
                        System.out.println("Part 2: " + boxId);
                        System.exit(0);
                    }
                }
            }
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        boxIds = FileReader.readAllLines(this.inputFile);
    }
}
