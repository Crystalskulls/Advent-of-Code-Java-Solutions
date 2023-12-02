package com.github.crystalskulls.aoc._2022.day4;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class Day4 extends Puzzle {

    private List<List<List<Integer>>> sectionsOfElfPairs;

    public Day4() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2022/day4/input.txt";
    }

    @Override
    protected void solvePart1() {
        int count = 0;
        for (List<List<Integer>> sectionsOfElfPair: this.sectionsOfElfPairs) {
            List<Integer> sectionsOfElf1 = sectionsOfElfPair.getFirst();
            List<Integer> sectionsOfElf2 = sectionsOfElfPair.getLast();
            if (new HashSet<>(sectionsOfElf1).containsAll(sectionsOfElf2) ||
                    new HashSet<>(sectionsOfElf2).containsAll(sectionsOfElf1)) {
                count++;
            }
        }
        System.out.println("Part 1: " + count);
    }

    @Override
    protected void solvePart2() {
        int count = 0;
        for (List<List<Integer>> sectionsOfElfPair: this.sectionsOfElfPairs) {
            List<Integer> interSectionList = ListUtils.intersection(sectionsOfElfPair.getFirst(), sectionsOfElfPair.getLast());
            if(!interSectionList.isEmpty()) {
                count++;
            }
        }
        System.out.println("Part 2: " + count);
    }

    @Override
    protected void readInputData(String inputFile) {
        List<List<Integer>> pairsOfElves = new ArrayList<>();
        List<String> lines = FileReader.readAllLines(inputFile);
        lines.forEach(line -> {
            String[] sections = line.split(",");
            int elf1StartSection = Integer.parseInt(sections[0].split("-")[0]);
            int elf1EndSection = Integer.parseInt(sections[0].split("-")[1]);
            int elf2StartSection = Integer.parseInt(sections[1].split("-")[0]);
            int elf2EndSection = Integer.parseInt(sections[1].split("-")[1]);
            pairsOfElves.add(IntStream.rangeClosed(elf1StartSection, elf1EndSection).boxed().toList());
            pairsOfElves.add(IntStream.rangeClosed(elf2StartSection, elf2EndSection).boxed().toList());
        });
        this.sectionsOfElfPairs = ListUtils.partition(pairsOfElves, 2);
    }
}
