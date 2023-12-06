package com.github.crystalskulls.aoc._2023.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.github.crystalskulls.aoc.common.Regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends Puzzle {

    private List<String> engineSchematics = new ArrayList<>();
    private final Pattern numberPattern = new Regex().numberPattern;
    private final Pattern symbolPattern = Pattern.compile("[^.\\d\\s]");
    private final Pattern starSymbolPattern = Pattern.compile("\\*");
    private List<Number> numbers;
    private List<Symbol> symbols;

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        findNumbersAndSymbols(this.symbolPattern);
        System.out.println("Part 1: " + findAndSumAdjacentNumbers());
    }

    @Override
    protected void solvePart2() {
        this.findNumbersAndSymbols(starSymbolPattern);
        int sum = 0;
        this.findAndSumAdjacentNumbers();
        for (Symbol symbol : this.symbols) {
            if(symbol.getAdjacentNumbers().size() == 2) {
                sum += symbol.getAdjacentNumbers().stream().map(Number::getValue).reduce(1, (n, m) -> n * m);
            }
        }
        System.out.println("Part 2: " + sum);
    }

    @Override
    protected void readInputData(String inputFile) {
        this.engineSchematics = FileReader.readAllLines(inputFile);
    }

    private void findNumbersAndSymbols(Pattern symbolPattern) {
        this.numbers = new ArrayList<>();
        this.symbols = new ArrayList<>();
        for (int i = 0; i < engineSchematics.size(); i++) {
            Matcher numberMatcher = numberPattern.matcher(engineSchematics.get(i));
            Matcher symbolMatcher = symbolPattern.matcher(engineSchematics.get(i));

            while(numberMatcher.find()) {
                numbers.add(new Number(
                        Integer.parseInt(numberMatcher.group()),
                        List.of(numberMatcher.start(), numberMatcher.end()-1),
                        i)
                );
            }

            while(symbolMatcher.find()) {
                symbols.add(new Symbol(symbolMatcher.start(), i));
            }
        }
    }

    private int findAndSumAdjacentNumbers() {
        int sum = 0;
        for (Symbol symbol : symbols) {
            for (Number number : numbers) {
                int x = symbol.getX(), y = symbol.getY();
                List<Integer> adjacentXCoordinates = List.of(x-1, x, x+1);
                List<Integer> adjacentYCoordinates = List.of(y-1, y, y+1);
                if(adjacentXCoordinates.stream().anyMatch(adjacentXCoordinate -> number.getXCoordinates().contains(adjacentXCoordinate)) && adjacentYCoordinates.contains(number.getY())) {
                    sum += number.getValue();
                    symbol.getAdjacentNumbers().add(number);
                }
            }
        }
        return sum;
    }
}
