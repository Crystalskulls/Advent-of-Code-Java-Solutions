package com.github.crystalskulls.aoc;

import com.github.crystalskulls.aoc._2022.day1.Day1;
import com.github.crystalskulls.aoc._2022.day3.Day3;
import com.github.crystalskulls.aoc._2022.day4.Day4;
import com.github.crystalskulls.aoc._2022.day5.Day5;
import com.github.crystalskulls.aoc._2022.day6.Day6;
import com.github.crystalskulls.aoc._2023.day10.Day10;
import com.github.crystalskulls.aoc._2023.day11.Day11;
import com.github.crystalskulls.aoc._2023.day12.Day12;
import com.github.crystalskulls.aoc._2023.day13.Day13;
import com.github.crystalskulls.aoc._2023.day14.Day14;
import com.github.crystalskulls.aoc._2023.day15.Day15;
import com.github.crystalskulls.aoc._2023.day2.Day2;
import com.github.crystalskulls.aoc._2023.day7.Day7;
import com.github.crystalskulls.aoc._2023.day8.Day8;
import com.github.crystalskulls.aoc._2023.day9.Day9;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.cli.CommandLine;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AdventOfCode {

    private static final Map<Integer, Map<Integer, ? extends Puzzle>> puzzleMap = Map.of(
            2015, Map.of(
                    1, new com.github.crystalskulls.aoc._2015.day1.Day1(),
                    2, new com.github.crystalskulls.aoc._2015.day2.Day2(),
                    3, new com.github.crystalskulls.aoc._2015.day3.Day3(),
                    4, new com.github.crystalskulls.aoc._2015.day4.Day4(),
                    5, new com.github.crystalskulls.aoc._2015.day5.Day5(),
                    6, new com.github.crystalskulls.aoc._2015.day6.Day6(),
                    8, new com.github.crystalskulls.aoc._2015.day8.Day8()

            ),
            2022, Map.of(
                    1, new Day1(),
                    2, new com.github.crystalskulls.aoc._2022.day2.Day2(),
                    3, new Day3(),
                    4, new Day4(),
                    5, new Day5(),
                    6, new Day6()
            ),
            2023, Stream.concat(Map.of(
                    1, new com.github.crystalskulls.aoc._2023.day1.Day1(),
                    2, new Day2(),
                    3, new com.github.crystalskulls.aoc._2023.day3.Day3(),
                    4, new com.github.crystalskulls.aoc._2023.day4.Day4(),
                    5, new com.github.crystalskulls.aoc._2023.day5.Day5(),
                    6, new com.github.crystalskulls.aoc._2023.day6.Day6(),
                    7, new Day7(),
                    8, new Day8(),
                    9, new Day9(),
                    10, new Day10()
            ).entrySet().stream(), Map.of(
                    11, new Day11(),
                    12, new Day12(),
                    13, new Day13(),
                    14, new Day14(),
                    15, new Day15()
            ).entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
            2024, Stream.concat(Map.of(
                    1, new com.github.crystalskulls.aoc._2024.day1.Day1(),
                    2, new com.github.crystalskulls.aoc._2024.day2.Day2(),
                    3, new com.github.crystalskulls.aoc._2024.day3.Day3(),
                    4, new com.github.crystalskulls.aoc._2024.day4.Day4(),
                    5, new com.github.crystalskulls.aoc._2024.day5.Day5(),
                    6, new com.github.crystalskulls.aoc._2024.day6.Day6(),
                    7, new com.github.crystalskulls.aoc._2024.day7.Day7(),
                    9, new com.github.crystalskulls.aoc._2024.day9.Day9(),
                    10, new com.github.crystalskulls.aoc._2024.day10.Day10(),
                    11, new com.github.crystalskulls.aoc._2024.day11.Day11()
            ).entrySet().stream(), Map.of(
                    12, new com.github.crystalskulls.aoc._2024.day12.Day12(),
                    13, new com.github.crystalskulls.aoc._2024.day13.Day13(),
                    14, new com.github.crystalskulls.aoc._2024.day14.Day14(),
                    15, new com.github.crystalskulls.aoc._2024.day15.Day15(),
                    16, new com.github.crystalskulls.aoc._2024.day16.Day16(),
                    17, new com.github.crystalskulls.aoc._2024.day17.Day17(),
                    18, new com.github.crystalskulls.aoc._2024.day18.Day18(),
                    19, new com.github.crystalskulls.aoc._2024.day19.Day19(),
                    20, new com.github.crystalskulls.aoc._2024.day20.Day20(),
                    22, new com.github.crystalskulls.aoc._2024.day22.Day22()
            ).entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
    );

    public static void main(String[] args) throws Exception {
        CommandLine cmd = CommandLineInterface.parseArguments(args);
        int year = Integer.parseInt(cmd.getOptionValue("year"));
        int day = Integer.parseInt(cmd.getOptionValue("day"));
        System.out.printf("Solving Puzzle %d of year %d\n", day, year);
        Puzzle puzzle = puzzleMap.get(year).get(day);
        puzzle.solve();
    }
}