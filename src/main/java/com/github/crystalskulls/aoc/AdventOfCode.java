package com.github.crystalskulls.aoc;

import com.github.crystalskulls.aoc._2022.day1.Day1;
import com.github.crystalskulls.aoc._2022.day2.Day2;
import com.github.crystalskulls.aoc._2022.day3.Day3;
import com.github.crystalskulls.aoc._2022.day4.Day4;
import com.github.crystalskulls.aoc._2022.day5.Day5;
import com.github.crystalskulls.aoc._2022.day6.Day6;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.cli.CommandLine;

import java.util.Map;


public class AdventOfCode {

    private static final Map<Integer, Map<Integer, ? extends Puzzle>> puzzleMap = Map.of(
            2015, Map.of(
                    1, new com.github.crystalskulls.aoc._2015.day1.Day1(),
                    2, new com.github.crystalskulls.aoc._2015.day2.Day2()
            ),
            2022, Map.of(
                    1, new Day1(),
                    2, new Day2(),
                    3, new Day3(),
                    4, new Day4(),
                    5, new Day5(),
                    6, new Day6()
            ),
            2023, Map.of(
                    1, new com.github.crystalskulls.aoc._2023.day1.Day1(),
                    2, new com.github.crystalskulls.aoc._2023.day2.Day2()
            )
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