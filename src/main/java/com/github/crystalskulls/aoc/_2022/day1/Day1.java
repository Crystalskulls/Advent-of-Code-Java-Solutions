package com.github.crystalskulls.aoc._2022.day1;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day1 extends Puzzle {

    private List<Elf> elves;

    public Day1() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2022/day1/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + this.elves.get(this.elves.size() -1).getCalories().stream().reduce(0, Integer::sum));
    }

    @Override
    protected void solvePart2() {
        int firstPlace = this.elves.size();
        int thirdPlace = firstPlace -3;
        List<Elf> topThreeElves = this.elves.subList(thirdPlace, firstPlace);
        System.out.println("Part 2: " +
                topThreeElves.
                        stream().
                        map(elf -> elf.getCalories().stream().reduce(0, Integer::sum)).
                        toList().
                        stream().
                        reduce(0, Integer::sum)
        );
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> caloriesPerElf = FileReader.readAllLines(inputFile, "\\R\\R");
        this.elves = parseToListOfElves(caloriesPerElf);
        Collections.sort(this.elves);
    }

    private List<Elf> parseToListOfElves(List<String> caloriesPerElf) {
        List<Elf> elves = new ArrayList<>();
        caloriesPerElf.forEach(calories -> {
            Elf elf = new Elf();
            elf.setCalories(Arrays.stream(calories.split("\\R")).map(Integer::valueOf).toList());
            elves.add(elf);
        });
        return elves;
    }
}
