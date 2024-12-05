package com.github.crystalskulls.aoc._2024.day5;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day5 extends Puzzle {

    private List<List<Integer>> printerUpdates;
    private HashMap<Integer, List<Integer>> pageOrderingRules = new HashMap<>();
    List<List<Integer>> incorrectOrderedPrinterUpdates = new ArrayList<>();

    public Day5() {
    }

    @Override
    protected void solvePart1() {
        List<List<Integer>> correctOrderedPrinterUpdates = new ArrayList<>();
        for (List<Integer> printerUpdate : printerUpdates) {
            boolean correctOrdered = true;
            List<Integer> previousNumbers = new ArrayList<>();
            for (int i = 1; i < printerUpdate.size(); i++) {
                previousNumbers.add(printerUpdate.get(i-1));
                Integer currentNumber = printerUpdate.get(i);
                List<Integer> numbersAfter = pageOrderingRules.get(currentNumber);
                if(numbersAfter != null && previousNumbers.stream().anyMatch(numbersAfter::contains)) {
                    correctOrdered = false;
                    break;
                }
            }
            if(correctOrdered) {
                correctOrderedPrinterUpdates.add(printerUpdate);
            } else {
                incorrectOrderedPrinterUpdates.add(printerUpdate);
            }
        }
        System.out.println("Part 1: " + correctOrderedPrinterUpdates.stream().mapToInt(printerUpdate -> printerUpdate.get((printerUpdate.size()-1) / 2)).sum());
    }

    @Override
    protected void solvePart2() {
        List<List<Integer>> correctOrderedPrinterUpdates = new ArrayList<>();
        for (List<Integer> incorrectOrderedPrinterUpdate : incorrectOrderedPrinterUpdates) {
            List<Integer> tmpIncorrectOrderedPrinterUpdate = new ArrayList<>(incorrectOrderedPrinterUpdate);
            while (true) {
                List<Integer> tmpList = new ArrayList<>(tmpIncorrectOrderedPrinterUpdate);
                boolean correctOrdered = true;
                List<Integer> previousNumbers = new ArrayList<>();
                for (int i = 1; i < tmpList.size(); i++) {
                    previousNumbers.add(tmpList.get(i-1));
                    Integer currentNumber = tmpList.get(i);
                    List<Integer> numbersAfter = pageOrderingRules.get(currentNumber);
                    if(numbersAfter != null && previousNumbers.stream().anyMatch(numbersAfter::contains)) {
                        Integer l = tmpList.get(i-1);
                        Integer r = tmpList.get(i);
                        tmpIncorrectOrderedPrinterUpdate.set(i-1, r);
                        tmpIncorrectOrderedPrinterUpdate.set(i, l);
                        correctOrdered = false;
                        break;
                    }
                }
                if(correctOrdered) {
                    correctOrderedPrinterUpdates.add(tmpIncorrectOrderedPrinterUpdate);
                    break;
                }
            }
        }
        System.out.println("Part 2: " + correctOrderedPrinterUpdates.stream().mapToInt(printerUpdate -> printerUpdate.get((printerUpdate.size()-1) / 2)).sum());
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> printerUpdates = FileReader.readAllLines("src/main/java/com/github/crystalskulls/aoc/_2024/day5/printer_updates.txt");
        this.printerUpdates = printerUpdates.stream().map(update -> {
            List<Integer> numbers = new ArrayList<>();
            for (String n : update.split(",")) {
                numbers.add(Integer.parseInt(n));
            }
            return numbers;
        }).toList();

        List<String> pageOrderingRules = FileReader.readAllLines("src/main/java/com/github/crystalskulls/aoc/_2024/day5/page_ordering_rules.txt");
        for (String pageOrderingRule : pageOrderingRules) {
            String[] numbers = pageOrderingRule.split("\\|");
            this.pageOrderingRules.computeIfAbsent(Integer.parseInt(numbers[0]), k -> new ArrayList<>());
            this.pageOrderingRules.get(Integer.parseInt(numbers[0])).add(Integer.parseInt(numbers[1]));
        }
    }
}