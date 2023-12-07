package com.github.crystalskulls.aoc._2023.day7;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day7 extends Puzzle {

    private List<Hand> orderedHands = new ArrayList<>();
    private final HandComparator handComparator = new HandComparator();
    private final HandComparatorWithJokerRule handComparatorWithJokerRule = new HandComparatorWithJokerRule();

    public Day7() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day7/input.txt";
    }

    @Override
    protected void solvePart1() {
        this.orderedHands = this.orderedHands.stream().sorted(this.handComparator).toList();
        System.out.println("Part 1: " + this.calculateTotalWinnings());
    }

    @Override
    protected void solvePart2() {
        this.orderedHands = this.orderedHands.stream().sorted(this.handComparatorWithJokerRule).toList();
        System.out.println("Part 2: " + this.calculateTotalWinnings());
    }

    private int calculateTotalWinnings() {
        int totalWinnings = 0;
        for (int i = 0; i < this.orderedHands.size(); i++) {
            totalWinnings += this.orderedHands.get(i).getBid() * (i+1);
        }
        return totalWinnings;
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> listOfHands = FileReader.readAllLines(inputFile);
        listOfHands.forEach(hand -> {
            String[] snippets = hand.split(" ");
            this.orderedHands.add(new Hand(snippets[0], Integer.parseInt(snippets[1])));
        });
    }
}
