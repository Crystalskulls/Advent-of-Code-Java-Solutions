package com.github.crystalskulls.aoc._2024.day7;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.google.common.collect.Sets;

import java.util.*;

public class Day7 extends Puzzle {

    List<Equation> calibrationEquations = new ArrayList<>();
    Set<Equation> passedEquations = new HashSet<>();

    public Day7() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day7/input.txt";
    }

    @Override
    protected void solvePart1() {
        Set<String> operators = Set.of("+", "*");
        testEquations(operators);
        System.out.println("Part 1: " + passedEquations.stream().mapToLong(e -> e.testValue).sum());
    }

    @Override
    protected void solvePart2() {
        Set<String> operators = Set.of("+", "*", "||");
        passedEquations = new HashSet<>();
        testEquations(operators);
        System.out.println("Part 2: " + passedEquations.stream().mapToLong(e -> e.testValue).sum());
    }

    private void testEquations(Set<String> operators) {
        for (Equation equation : calibrationEquations) {
            List<Set<String>> operatorChoices = new ArrayList<>();
            for (int i = 0; i < equation.numbers.size() - 1; i++) {
                operatorChoices.add(Set.copyOf(operators));
            }
            Set<List<String>> cartesianProduct = Sets.cartesianProduct(operatorChoices);

            for (List<String> operatorCombination : cartesianProduct) {
                long r = calculate(equation, operatorCombination);
                if(r == equation.testValue) {
                    passedEquations.add(equation);
                }
            }
        }
    }

    private static long calculate(Equation equation, List<String> operatorCombination) {
        long r = equation.numbers.getFirst();
        for (int i = 1; i < equation.numbers.size(); i++) {
            String operator = operatorCombination.get(i-1);
            if(operator.equals("+")) {
                r += equation.numbers.get(i);
            } else if (operator.equals("||")) {
                r = Long.parseLong("" + r + equation.numbers.get(i));
            } else {
                r *= equation.numbers.get(i);
            }
        }
        return r;
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        for (String equation : lines) {
            String[] snippets = equation.split(": ");
            calibrationEquations.add(new Equation(Long.parseLong(snippets[0]),
                    Arrays.stream(snippets[1].split(" ")).map(Long::parseLong).toList()));
        }
    }

    record Equation(Long testValue, List<Long> numbers){}
}