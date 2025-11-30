package com.github.crystalskulls.aoc._2019.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.ToString;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2DFormat;

import java.util.*;
import java.util.stream.IntStream;

public class Day3 extends Puzzle {

    private Wire wireOne;
    private Wire wireTwo;

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2019/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println(wireOne.coordinates);
        for (Coordinate coordinate : wireOne.coordinates) {
            if(coordinate.x == 0 && coordinate.y == 0) {
                continue;
            }
            if(wireTwo.coordinates.contains(coordinate)) {
                System.out.println(Math.abs(coordinate.x) + Math.abs(coordinate.y));
                break;
            }
        }
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> wirePaths = FileReader.readAllLines(inputFile);
        wireOne = new Wire(Arrays.stream(wirePaths.getFirst().split(",")).map(this::creatInstruction).toList());
        wireTwo = new Wire(Arrays.stream(wirePaths.getLast().split(",")).map(this::creatInstruction).toList());
    }

    @ToString
    private static class Wire {
        List<Instruction> instructions;
        List<Coordinate> coordinates;

        Wire(List<Instruction> instructions) {
            this.instructions = new ArrayList<>(instructions);
            calculateCoordinates();
        }

        private void calculateCoordinates() {
            Vector2D currentCoordinate = new Vector2D(0, 0);
            Vector2D nextCoordinate = new Vector2D(0, 0);
            coordinates = new ArrayList<>();
            for (Instruction instruction : instructions) {
                Vector2D finalCurrentCoordinate = currentCoordinate;
                Vector2D finalCurrentCoordinate1 = currentCoordinate;
                Vector2D finalCurrentCoordinate2 = currentCoordinate;
                Vector2D finalCurrentCoordinate3 = currentCoordinate;
                switch (instruction.direction) {
                    case UP -> {
                        nextCoordinate = currentCoordinate.add(new Vector2D(0, instruction.steps));
                        coordinates.addAll(IntStream.rangeClosed((int) currentCoordinate.getY(), (int) nextCoordinate.getY()).boxed().map(y -> new Coordinate((int) finalCurrentCoordinate3.getX(), y)).toList());
                    }
                    case DOWN -> {
                        nextCoordinate = currentCoordinate.add(new Vector2D(0, instruction.steps * -1));
                        coordinates.addAll(IntStream.rangeClosed((int) nextCoordinate.getY(), (int) currentCoordinate.getY()).boxed().map(y -> new Coordinate((int) finalCurrentCoordinate2.getX(), y)).toList());
                    }
                    case LEFT -> {
                        nextCoordinate = currentCoordinate.add(new Vector2D(instruction.steps * -1, 0));
                        coordinates.addAll(IntStream.rangeClosed((int) nextCoordinate.getX(), (int) currentCoordinate.getX()).boxed().map(x -> new Coordinate(x, (int) finalCurrentCoordinate1.getY())).toList());
                    }
                    case RIGHT -> {
                        nextCoordinate = currentCoordinate.add(new Vector2D(instruction.steps, 0));
                        coordinates.addAll(IntStream.rangeClosed((int) currentCoordinate.getX(), (int) nextCoordinate.getX()).boxed().map(x -> new Coordinate(x, (int) finalCurrentCoordinate.getY())).toList());
                    }
                }
                currentCoordinate = nextCoordinate;
            }
            coordinates.sort(Comparator.comparingInt(c -> c.x + c.y));
        }
    }

    private record Coordinate (int x, int y) {}

    private record Instruction(Direction direction, int steps) {}

    Instruction creatInstruction(String directionAndSteps) {
        char abbreviation = directionAndSteps.charAt(0);
        int steps = Integer.parseInt(String.valueOf(directionAndSteps.charAt(1)));
        Direction direction = Direction.valueOfAbbreviation(abbreviation);
        return new Instruction(direction, steps);
    }

    private enum Direction {
        UP('U'),
        DOWN('D'),
        LEFT('L'),
        RIGHT('R');

        final char abbreviation;

        Direction(char abbreviation) {
            this.abbreviation = abbreviation;
        }

        static Direction valueOfAbbreviation(char abbreviation) {
            for (Direction direction : values()) {
                if (Objects.equals(direction.abbreviation, abbreviation)) {
                    return direction;
                }
            }
            throw new RuntimeException("Invalid abbreviation");
        }
    }
}