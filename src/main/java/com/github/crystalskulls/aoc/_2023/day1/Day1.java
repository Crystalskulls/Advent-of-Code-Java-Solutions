package com.github.crystalskulls.aoc._2023.day1;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 extends Puzzle {

    private List<String> calibrationDocument;
    private final Map<String, String> wordToDigitMap = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    public Day1() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day1/input.txt";
    }

    @Override
    protected void solvePart1() {
        int sumCalibrationValues = 0;
        Pattern firstDigitPattern = Pattern.compile("\\d");
        Pattern lastDigitPattern = Pattern.compile("\\d(?!.*\\d)");
        for (String line : this.calibrationDocument) {
            Matcher firstDigitMatcher = firstDigitPattern.matcher(line);
            Matcher lastDigitMatcher = lastDigitPattern.matcher(line);
            firstDigitMatcher.find();
            lastDigitMatcher.find();
            sumCalibrationValues += Integer.parseInt(firstDigitMatcher.group() + lastDigitMatcher.group());
        }
        System.out.println("Part 1: " + sumCalibrationValues);
    }

    @Override
    protected void solvePart2() {
        Pattern letterPattern = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9)");
        int sumCalibrationValues = 0;
        for (String line : this.calibrationDocument) {
            Matcher matcher = letterPattern.matcher(line);
            matcher.find();
            String firstDigit = matcher.group();
            String lastDigit = firstDigit;
            int start = matcher.start() + 1;
            while(matcher.find(start)) {
                lastDigit = matcher.group();
                start = matcher.start()+1;
            }
            firstDigit = firstDigit.length() > 1 ? this.wordToDigitMap.get(firstDigit) : firstDigit;
            lastDigit = lastDigit.length() > 1 ? this.wordToDigitMap.get(lastDigit) : lastDigit;
            sumCalibrationValues += Integer.parseInt(firstDigit + lastDigit);
        }
        System.out.println("Part 2: " + sumCalibrationValues);
    }

    @Override
    protected void readInputData(String inputFile) {
        this.calibrationDocument = FileReader.readAllLines(inputFile);
    }
}
