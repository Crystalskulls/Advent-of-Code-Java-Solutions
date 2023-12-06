package com.github.crystalskulls.aoc._2023.day4;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 extends Puzzle {

    private final List<Scratchcard> scratchcards = new ArrayList<>();
    private final List<List<Scratchcard>> totalScratchcards = new ArrayList<>();
    private final Pattern numberPattern = Pattern.compile("\\d+");
    private int sum = 0;

    public Day4() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day4/input.txt";
    }

    @Override
    protected void solvePart1() {
        for (Scratchcard scratchcard : this.scratchcards) {
            int correctNumbers = scratchcard.findMatchingNumbers();
            this.sum += correctNumbers == 0 ? 0 : (int) Math.pow(2, correctNumbers -1);
        }
        System.out.println("Part 1: " + this.sum);
    }

    @Override
    protected void solvePart2() {
        this.sum = 0;
        initializeTotalScratchcardsList();

        for (int i = 0; i < this.scratchcards.size(); i++) {
            Scratchcard currentScratchcard = this.scratchcards.get(i);
            int currentCardAmount = this.totalScratchcards.get(i).size();
            this.totalScratchcards.get(i).add(currentScratchcard);
            int correctNumbers = currentScratchcard.findMatchingNumbers();

            List<Scratchcard> scratchcardsWon = this.scratchcards.subList(i+1, i+1+correctNumbers);
            copyScratchcards(currentCardAmount, scratchcardsWon, i);
            sum += currentCardAmount + 1;
        }
        System.out.println("Part 2: " + sum);
    }

    private void initializeTotalScratchcardsList() {
        for (Scratchcard ignored : this.scratchcards) {
            this.totalScratchcards.add(new ArrayList<>());
        }
    }

    private void copyScratchcards(int currentCardAmount, List<Scratchcard> scratchcardsWon, int i) {
        for(int j = 0; j < currentCardAmount +1; j++) {
            for (int k = 0; k < scratchcardsWon.size(); k++) {
                this.totalScratchcards.get(i +k+1).add(scratchcardsWon.get(k));
            }
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> scratchcards = FileReader.readAllLines(inputFile);
        for (String scratchcard : scratchcards) {
            String[] scratchcardSnippets = scratchcard.split("\\|");
            this.scratchcards.add(new Scratchcard(
                    findNumbers(scratchcardSnippets[0].split(":")[1]),
                    findNumbers(scratchcardSnippets[1])
            ));
        }
    }

    private List<Integer> findNumbers(String numberString) {
        Matcher matcher = this.numberPattern.matcher(numberString);
        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers;
    }
}