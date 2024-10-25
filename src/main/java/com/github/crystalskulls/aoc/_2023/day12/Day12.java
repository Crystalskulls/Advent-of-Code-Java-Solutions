package com.github.crystalskulls.aoc._2023.day12;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.github.crystalskulls.aoc.common.Regex;
import org.apache.commons.math3.util.Combinations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Puzzle {

    public Day12() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day12/input.txt";
    }

    @Override
    protected void solvePart1() {

    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        int sum = 0;
        for (String line : lines) {
            System.out.println(line);
            int valid = 0;
            String[] tmp = line.split(" ");
            List<Integer> groups = Regex.findNumbers(tmp[1], Regex.positiveNumberPattern, Integer::parseInt);
            Set<String> combinations = this.combinations(new ConditionRecord(tmp[0]));
            for (String combination : combinations) {
                Pattern pattern = Pattern.compile("#+");
                Matcher matcher = pattern.matcher(combination);
                List<Integer> matcherGroups = new ArrayList<>();
                while (matcher.find()) {
                    matcherGroups.add(matcher.group().length());
                }
                if(matcherGroups.equals(groups)) {
                    valid++;
                }
            }
            sum += valid;
        }
        System.out.println("Part 1: " + sum);
    }

    private Set<String> combinations(ConditionRecord conditionRecord) {
        String[] chars = {".", "#"};
        String unknownSpring = "\\?";
        Pattern pattern = Pattern.compile("\\?+");
        Matcher matcher = pattern.matcher(conditionRecord.getRow());
        List<Integer> unknownSpringGroupLengths = new ArrayList<>();
        while(matcher.find()) {
            unknownSpringGroupLengths.add(matcher.group().length());
        }
        List<ConditionRecord> conditionRecordQueue = new ArrayList<>();
        Set<String> combinations = new HashSet<>();
        conditionRecordQueue.add(conditionRecord);


        while(!conditionRecordQueue.isEmpty()) {
            System.out.println(conditionRecordQueue.size());
            ConditionRecord nextConditionRecord = conditionRecordQueue.remove(0);
            for (int i = 0; i < Math.pow(chars.length, unknownSpringGroupLengths.get(nextConditionRecord.getCurrentSpringGroup())); i++) {
                ConditionRecord currentConditionRecord = new ConditionRecord(nextConditionRecord.getRow());
                for (int j = 0; j < unknownSpringGroupLengths.get(nextConditionRecord.getCurrentSpringGroup()); j++) {
                    int index = (i & (1 << j)) == 0 ? 0 : 1;
                    currentConditionRecord.setRow(currentConditionRecord.getRow().replaceFirst(unknownSpring, chars[index]));
                }
                if (currentConditionRecord.getRow().contains("?")) {
                    conditionRecordQueue.add(currentConditionRecord);
                    currentConditionRecord.setCurrentSpringGroup(Math.min(currentConditionRecord.getCurrentSpringGroup() + 1, unknownSpringGroupLengths.size()));
                } else {
                    combinations.add(currentConditionRecord.getRow());
                }
            }
            /*System.out.println(conditionRecordQueue.size());
            conditionRecordQueue.forEach(System.out::println);
            System.exit(1);*/
        }
        /*System.out.println(combinations.size());
        combinations.forEach(System.out::println);*/

        return combinations;
    }
}
