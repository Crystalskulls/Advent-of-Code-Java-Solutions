package com.github.crystalskulls.aoc._2022.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.collections4.ListUtils;

import java.util.Arrays;
import java.util.List;

public class Day3 extends Puzzle {

    private List<Backpack> backpacks;

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2022/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        int totalPriority = 0;
        for (Backpack backpack: this.backpacks) {
            for (char item: backpack.getFirstCompartment()) {
                if(Arrays.binarySearch(backpack.getSecondCompartment(), item) >= 0) {
                    totalPriority = getPriority(item, totalPriority);
                    break;
                }
            }
        }
        System.out.println("Part 1: " + totalPriority);
    }

    @Override
    protected void solvePart2() {
        List<List<Backpack>> elfGroups = ListUtils.partition(this.backpacks, 3);
        int totalPriority = 0;
        for (List<Backpack> group: elfGroups) {
            for (char item: group.getFirst().getItems()) {
                if(Arrays.binarySearch(group.get(1).getItems(), item) >= 0 && Arrays.binarySearch(group.getLast().getItems(), item) >= 0) {
                    totalPriority = getPriority(item, totalPriority);
                    break;
                }
            }
        }
        System.out.println("Part 2: " + totalPriority);
    }

    @Override
    protected void readInputData(String inputFile) {
        this.backpacks = FileReader.readAllLines(inputFile)
                .stream()
                .map(Backpack::new)
                .toList();
    }

    private static int getPriority(char item, int totalPriority) {
        if(item > 64 && item < 91) {
            totalPriority += item - 38;
        } else {
            totalPriority += item - 96;
        }
        return totalPriority;
    }
}