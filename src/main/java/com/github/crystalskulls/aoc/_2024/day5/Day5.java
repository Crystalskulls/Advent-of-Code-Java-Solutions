package com.github.crystalskulls.aoc._2024.day5;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day5 extends Puzzle {

    private List<List<Integer>> pageUpdates;
    private final HashMap<Integer, List<Integer>> pageOrderingRules = new HashMap<>();
    private final List<List<Integer>> correctOrderedPageUpdates = new ArrayList<>();
    private final List<List<Integer>> incorrectOrderedPageUpdates = new ArrayList<>();

    public Day5() {
    }

    @Override
    protected void solvePart1() {
        pageUpdates.forEach(pages -> {
            if(violatesRules(pages))
                incorrectOrderedPageUpdates.add(pages);
            else
                correctOrderedPageUpdates.add(pages);
        });
        System.out.println("Part 1: " + correctOrderedPageUpdates.stream().mapToInt(printerUpdate -> printerUpdate.get((printerUpdate.size()-1) / 2)).sum());
    }

    private boolean violatesRules(List<Integer> pages) {
        List<Integer> previousPages = new ArrayList<>();
        for (int i = 1; i < pages.size(); i++) {
            Integer currentPage = pages.get(i);
            Integer previousPage = pages.get(i-1);
            previousPages.add(previousPage);
            List<Integer> rightPages = pageOrderingRules.get(currentPage);
            if(rightPages != null && previousPages.stream().anyMatch(rightPages::contains)) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void solvePart2() {
        List<List<Integer>> correctOrderedPageUpdates = new ArrayList<>();
        for (List<Integer> incorrectOrderedPageUpdate : incorrectOrderedPageUpdates) {
            List<Integer> currentPages = new ArrayList<>(incorrectOrderedPageUpdate);
            while (true) {
                List<Integer> pages = new ArrayList<>(currentPages);
                boolean correctOrdered = true;
                List<Integer> previousNumbers = new ArrayList<>();
                for (int i = 1; i < pages.size(); i++) {
                    previousNumbers.add(pages.get(i-1));
                    Integer currentNumber = pages.get(i);
                    List<Integer> numbersAfter = pageOrderingRules.get(currentNumber);
                    if(numbersAfter != null && previousNumbers.stream().anyMatch(numbersAfter::contains)) {
                        Integer l = pages.get(i-1);
                        Integer r = pages.get(i);
                        currentPages.set(i-1, r);
                        currentPages.set(i, l);
                        correctOrdered = false;
                        break;
                    }
                }
                if(correctOrdered) {
                    correctOrderedPageUpdates.add(currentPages);
                    break;
                }
            }
        }
        System.out.println("Part 2: " + correctOrderedPageUpdates.stream().mapToInt(printerUpdate -> printerUpdate.get((printerUpdate.size()-1) / 2)).sum());
    }

    @Override
    protected void readInputData(String inputFile) {
        parseUpdates();
        parsePageOrderingRules();
    }

    private void parseUpdates() {
        List<String> pageUpdates = FileReader.readAllLines("src/main/java/com/github/crystalskulls/aoc/_2024/day5/printer_updates.txt");
        this.pageUpdates = pageUpdates.stream().map(update -> {
            List<Integer> numbers = new ArrayList<>();
            for (String n : update.split(",")) {
                numbers.add(Integer.parseInt(n));
            }
            return numbers;
        }).toList();
    }

    private void parsePageOrderingRules() {
        List<String> pageOrderingRules = FileReader.readAllLines("src/main/java/com/github/crystalskulls/aoc/_2024/day5/page_ordering_rules.txt");
        for (String pageOrderingRule : pageOrderingRules) {
            String[] numbers = pageOrderingRule.split("\\|");
            this.pageOrderingRules.computeIfAbsent(Integer.parseInt(numbers[0]), k -> new ArrayList<>());
            this.pageOrderingRules.get(Integer.parseInt(numbers[0])).add(Integer.parseInt(numbers[1]));
        }
    }
}