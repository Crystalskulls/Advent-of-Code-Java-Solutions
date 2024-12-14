package com.github.crystalskulls.aoc._2024.day13;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends Puzzle {

    List<ClawMachine> clawMachines = new ArrayList<>();
    Pattern numberPattern = Pattern.compile("\\d+");

    public Day13() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day13/input.txt";
    }

    @Override
    protected void solvePart1() {
        long tokens = 0;
        long counterButtonA;
        long counterButtonB;
        for (ClawMachine clawMachine : clawMachines) {
            long quotientY = clawMachine.prize.y / clawMachine.buttonB.y;
            for (counterButtonB = quotientY; counterButtonB > 0L ; counterButtonB--) {
                long deltaY = clawMachine.prize.y - (counterButtonB * clawMachine.buttonB.y);
                if(deltaY % clawMachine.buttonA.y == 0) {
                    counterButtonA = deltaY / clawMachine.buttonA.y;
                    if((counterButtonA * clawMachine.buttonA.x + counterButtonB * clawMachine.buttonB.x) == clawMachine.prize.x &&
                            (counterButtonA * clawMachine.buttonA.y + counterButtonB * clawMachine.buttonB.y) == clawMachine.prize.y) {
                        tokens += counterButtonA * 3 + counterButtonB;
                        break;
                    }
                }
            }
        }
        System.out.println("Part 1: " + tokens);
    }

    @Override
    protected void solvePart2() {
        long tokens = 0;
        for (ClawMachine clawMachine : clawMachines) {
            long counterButtonA = Math.abs(((clawMachine.prize.x + 10000000000000L) * clawMachine.buttonB.y - (clawMachine.prize.y + 10000000000000L) * clawMachine.buttonB.x) / (clawMachine.buttonA.x * clawMachine.buttonB.y - clawMachine.buttonA.y * clawMachine.buttonB.x));
            long counterButtonB = Math.abs(((clawMachine.prize.x + 10000000000000L) * clawMachine.buttonA.y - (clawMachine.prize.y + 10000000000000L) * clawMachine.buttonA.x) / (clawMachine.buttonA.x * clawMachine.buttonB.y - clawMachine.buttonA.y * clawMachine.buttonB.x));
            if(counterButtonA * clawMachine.buttonA.x + counterButtonB * clawMachine.buttonB.x == (clawMachine.prize.x + 10000000000000L) && counterButtonA * clawMachine.buttonA.y + counterButtonB * clawMachine.buttonB.y == (clawMachine.prize.y + 10000000000000L)) {
                tokens += counterButtonA * 3 + counterButtonB;
            }
        }
        System.out.println("Part 2: " + tokens);
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> clawMachines = FileReader.readAllLines(inputFile, "\n\n");
        for (String clawMachine : clawMachines) {
            String[] snippets = clawMachine.split("\n");
            this.clawMachines.add(new ClawMachine(parseButton(snippets[0]), parseButton(snippets[1]), parsePrize(snippets[2])));
        }
    }

    private Button parseButton(String s) {
        return new Button(parseX(s), parseY(s));
    }

    private Prize parsePrize(String s) {
        return new Prize(parseX(s), parseY(s));
    }

    private long parseX(String s) {
        Matcher numberMatcher = numberPattern.matcher(s);
        numberMatcher.find();
        return Long.parseLong(numberMatcher.group());
    }

    private long parseY(String s) {
        Matcher numberMatcher = numberPattern.matcher(s);
        numberMatcher.find();
        numberMatcher.find();
        return Long.parseLong(numberMatcher.group());
    }

    @AllArgsConstructor
    private static class ClawMachine {
        Button buttonA;
        Button buttonB;
        Prize prize;

        @Override
        public String toString() {
            return "Button A: X+" + buttonA.x + ", Y+" + buttonA.y + "\n" + "Button B: X+" + buttonB.x + ", Y+" + buttonB.y + "\n" + "Prize: X=" + prize.x + ", Y=" + prize.y + "\n";
        }
    }

    record Button(long x, long y){}
    record Prize(long x, long y){}
}