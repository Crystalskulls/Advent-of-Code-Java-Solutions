package com.github.crystalskulls.aoc._2022.day5;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.collections4.ListUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day5 extends Puzzle {

    private Stack<Character>[] stacks;
    private List<Integer> rearrangementInstructions;
    private String stacksOfCratesInputFile;
    private String rearrangementProcedureInputFile;
    private List<List<Integer>> instructionSets;

    public Day5() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2022/day5/stacksOfCrates.txt src/main/java/com/github/crystalskulls/aoc/_2022/day5/rearrangementProcedure.txt";
    }

    @Override
    protected void solvePart1() {
        this.initStacks();
        this.instructionSets.forEach(instructionSet -> {
            for (int i = 0; i < instructionSet.getFirst(); i++) {
                this.stacks[instructionSet.getLast() - 1].push(this.stacks[instructionSet.get(1) - 1].pop());
            }
        });
        System.out.print("Part 1: ");
        printStack();
    }

    @Override
    protected void solvePart2() {
        this.initStacks();
        this.instructionSets.forEach(instructionSet -> {
            Stack<Character> stack = new Stack<>();
            for(int i = 0; i < instructionSet.getFirst(); i++) {
                stack.push(this.stacks[instructionSet.get(1) - 1].pop());
            }
            int count = stack.size();
            for(int k = 0; k < count; k++) {
                this.stacks[instructionSet.getLast() - 1].push(stack.pop());
            }
        });
        System.out.print("Part 2: ");
        printStack();
    }

    @Override
    protected void readInputData(@NotNull String inputFiles) {
        String[] files = inputFiles.split(" ");
        this.stacksOfCratesInputFile = files[0];
        this.rearrangementProcedureInputFile = files[1];
        this.parseRearrangementInstructions();
    }

    private void printStack() {
        for (Stack<Character> stack : this.stacks) {
            System.out.print(stack.peek());
        }
        System.out.println();
    }

    private void initStacks() {
        List<String> lines = FileReader.readAllLines(this.stacksOfCratesInputFile);
        int charCount = lines.getFirst().toCharArray().length;
        int numberOfStacks = (charCount / 4) + 1;
        this.stacks = new Stack[numberOfStacks];
        for (int i = 0; i < numberOfStacks; i++) {
            this.stacks[i] = new Stack<>();
        }
        this.parseStacksOfCrates(lines);
    }

    private void parseStacksOfCrates(@NotNull List<String> lines) {
        for (String line : lines.reversed()) {
            char[] crates = line.toCharArray();
            for (int i = 0; i < crates.length; i++) {
                if (i % 4 == 1 && !Character.isWhitespace(crates[i])) {
                    this.stacks[i / 4].push(crates[i]);
                }
            }
        }
    }

    private void parseRearrangementInstructions() {
        this.rearrangementInstructions = new ArrayList<>();
        List<String> lines = FileReader.readAllLines(this.rearrangementProcedureInputFile);
        lines.forEach(instruction -> {
            String[] instructionSet = instruction.split(" ");
            for (int i = 1; i < 6; i += 2) {
                this.rearrangementInstructions.add(Integer.valueOf(instructionSet[i]));
            }
        });
        this.instructionSets = ListUtils.partition(this.rearrangementInstructions, 3);
    }
}