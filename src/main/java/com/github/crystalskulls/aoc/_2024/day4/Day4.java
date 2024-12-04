package com.github.crystalskulls.aoc._2024.day4;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.MyStringUtils;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

public class Day4 extends Puzzle {

    List<String> linesColumnsAndDiagonals;
    List<String> lines;

    public Day4() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day4/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + linesColumnsAndDiagonals.stream().mapToInt(line -> StringUtils.countMatches(line, "XMAS") + StringUtils.countMatches(line, "SAMX")).sum());
    }

    @Override
    protected void solvePart2() {
        Set<String> set = Set.of("SAM", "MAS");
        int c = 0;
        for (int i = 1; i < lines.size() - 1; i++) {
            char[] chars = lines.get(i).toCharArray();
            for (int j = 1; j < chars.length - 1; j++) {
                if(chars[j] == 'A') {
                    if(set.contains(lines.get(i-1).charAt(j-1) + "A" + lines.get(i+1).charAt(j+1)) &&
                            set.contains(lines.get(i-1).charAt(j+1) + "A" + lines.get(i+1).charAt(j-1))) {
                        c++;
                    }
                }
            }
        }
        System.out.println("Part 2: " + c);
    }

    @Override
    protected void readInputData(String inputFile) {
        linesColumnsAndDiagonals = MyStringUtils.extractLinesColumnsAndDiagonals(inputFile);
        lines = FileReader.readAllLines(inputFile);
    }
}