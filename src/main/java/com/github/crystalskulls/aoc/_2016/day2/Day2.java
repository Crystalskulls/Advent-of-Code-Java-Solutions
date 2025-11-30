package com.github.crystalskulls.aoc._2016.day2;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 extends Puzzle {

    private final Map<Integer, Map<Character, Integer>> keypadMap = Map.of(
            1, Map.of('U', 1, 'R', 2, 'D', 4, 'L', 1),
            2, Map.of('U', 2, 'R', 3, 'D', 5, 'L', 1),
            3, Map.of('U', 3, 'R', 3, 'D', 6, 'L', 2),
            4, Map.of('U', 1, 'R', 5, 'D', 7, 'L', 4),
            5, Map.of('U', 2, 'R', 6, 'D', 8, 'L', 4),
            6, Map.of('U', 3, 'R', 6, 'D', 9, 'L', 5),
            7, Map.of('U', 4, 'R', 8, 'D', 7, 'L', 7),
            8, Map.of('U', 5, 'R', 9, 'D', 8, 'L', 7),
            9, Map.of('U', 6, 'R', 9, 'D', 9, 'L', 8)
    );
    private final Map<Character, Map<Character, Character>> advancedKeypad = Stream.concat(Map.of(
            '1', Map.of('U', '1', 'R', '1', 'D', '3', 'L', '1'),
            '2', Map.of('U', '2', 'R', '3', 'D', '6', 'L', '2'),
            '3', Map.of('U', '1', 'R', '4', 'D', '7', 'L', '2'),
            '4', Map.of('U', '4', 'R', '4', 'D', '8', 'L', '3'),
            '5', Map.of('U', '5', 'R', '6', 'D', '5', 'L', '5'),
            '6', Map.of('U', '2', 'R', '7', 'D', 'A', 'L', '5'),
            '7', Map.of('U', '3', 'R', '8', 'D', 'B', 'L', '6'),
            '8', Map.of('U', '4', 'R', '9', 'D', 'C', 'L', '7'),
            '9', Map.of('U', '9', 'R', '9', 'D', '9', 'L', '8'),
            'A', Map.of('U', '6', 'R', 'B', 'D', 'A', 'L', 'A')
    ).entrySet().stream(), Map.of(
            'B', Map.of('U', '7', 'R', 'C', 'D', 'D', 'L', 'A'),
            'C', Map.of('U', '8', 'R', 'C', 'D', 'C', 'L', 'B'),
            'D', Map.of('U', 'B', 'R', 'D', 'D', 'D', 'L', 'D')
                    ).entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    private List<List<Character>> instructions = new ArrayList<>();

    public Day2() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2016/day2/input.txt";
    }

    @Override
    protected void solvePart1() {
        int start = 5;
        for (List<Character> instructionLine : instructions) {
            for (Character instruction : instructionLine) {
                start = keypadMap.get(start).get(instruction);
            }
            System.out.print(start);
        }
        System.out.println();
    }

    @Override
    protected void solvePart2() {
        char start = '5';
        for (List<Character> instructionLine : instructions) {
            for (Character instruction : instructionLine) {
                start = advancedKeypad.get(start).get(instruction);
            }
            System.out.print(start);
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        instructions = FileReader.readAllLines(inputFile)
                .stream()
                .map(line -> line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
