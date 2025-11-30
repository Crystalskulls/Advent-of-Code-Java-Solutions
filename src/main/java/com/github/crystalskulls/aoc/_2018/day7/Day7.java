package com.github.crystalskulls.aoc._2018.day7;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Day7 extends Puzzle {

    SortedSet<Step> firstSteps = new TreeSet<>();

    public Day7() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2018/day7/input.txt";
    }

    @Override
    protected void solvePart1() {
        List<Character> orderedSteps = new ArrayList<>();
        Queue<Step> nextSteps = new PriorityQueue<>(firstSteps);
        while(!nextSteps.isEmpty()) {
            Step nextStep = nextSteps.poll();
            if(!nextStep.finished && nextStep.previousSteps.stream().allMatch(previousStep -> previousStep.finished)) {
                nextStep.finished = true;
                orderedSteps.add(nextStep.name);
                nextSteps.addAll(nextStep.nextSteps.stream().filter(step -> !step.finished).toList());
            }
        }
        orderedSteps.forEach(System.out::print);
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        Map<Character, Step> stepsByName = new HashMap<>();
        for (String line : lines.stream().toList()) {
            Step previousStep = stepsByName.computeIfAbsent(line.charAt(5), Step::new);
            Step nextStep = stepsByName.computeIfAbsent(line.charAt(36), Step::new);
            previousStep.nextSteps.add(nextStep);
            nextStep.previousSteps.add(previousStep);
        }
        firstSteps.addAll(stepsByName.values().stream().filter(step -> step.previousSteps.isEmpty()).toList());
    }

    @RequiredArgsConstructor
    private static class Step implements Comparable<Step> {
        private final char name;
        private final List<Step> previousSteps = new ArrayList<>();
        private final List<Step> nextSteps = new ArrayList<>();
        private boolean finished;

        @Override
        public int compareTo(@NotNull Day7.Step otherStep) {
            return Character.compare(this.name, otherStep.name);
        }
    }
}
