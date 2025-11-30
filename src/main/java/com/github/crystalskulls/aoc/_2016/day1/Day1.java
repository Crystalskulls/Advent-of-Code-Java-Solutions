package com.github.crystalskulls.aoc._2016.day1;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 extends Puzzle {

    Me me;
    List<String> instructions;

    public Day1() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2016/day1/input.txt";
    }

    @Override
    protected void solvePart1() {
        me = new Me(instructions);
        System.out.println("Part 1: " + me.walk(false));
    }

    @Override
    protected void solvePart2() {
        me = new Me(instructions);
        System.out.println("Part 2: " + me.walk(true));
    }

    @Override
    protected void readInputData(String inputFile) {
        instructions = FileReader.readAllLines(inputFile, ", ");
    }

    private class Me {
        Direction currentDirection;
        Vector2D currentPosition;
        Map<Vector2D, Integer> visitedPositions = new HashMap();
        List<String> instructions;

        Me(List<String> instructions) {
            this.currentDirection = Direction.NORTH;
            this.currentPosition = new Vector2D(0, 0);
            this.visitedPositions.put(this.currentPosition, 1);
            this.instructions = instructions;
        }

        int walk(boolean stopAtFirstLocationVisitiedTwice) {
            for (String instruction : instructions) {
                char direction = instruction.charAt(0);
                int steps = Integer.parseInt(instruction.substring(1));
                if(direction == 'R') {
                    switch (this.currentDirection) {
                        case NORTH -> move(stopAtFirstLocationVisitiedTwice, Direction.EAST, new Vector2D(1, 0), steps);
                        case EAST -> move(stopAtFirstLocationVisitiedTwice, Direction.SOUTH, new Vector2D(0, -1), steps);
                        case SOUTH -> move(stopAtFirstLocationVisitiedTwice, Direction.WEST, new Vector2D(-1, 0), steps);
                        case WEST -> move(stopAtFirstLocationVisitiedTwice, Direction.NORTH, new Vector2D(0, 1), steps);
                    }
                } else {
                    switch (this.currentDirection) {
                        case NORTH -> move(stopAtFirstLocationVisitiedTwice, Direction.WEST, new Vector2D(-1, 0), steps);
                        case WEST -> move(stopAtFirstLocationVisitiedTwice, Direction.SOUTH, new Vector2D(0, -1), steps);
                        case SOUTH -> move(stopAtFirstLocationVisitiedTwice, Direction.EAST, new Vector2D(1, 0), steps);
                        case EAST -> move(stopAtFirstLocationVisitiedTwice, Direction.NORTH, new Vector2D(0, 1), steps);
                    }
                }
            }
            return (int) (Math.abs(currentPosition.getX()) + Math.abs(currentPosition.getY()));
        }

        private void move(boolean stopAtFirstLocationVisitiedTwice, Direction direction, Vector2D vector2D, int steps) {
            this.currentDirection = direction;
            for (int i = 0; i < steps; i++) {
                this.currentPosition = this.currentPosition.add(vector2D);
                this.visitedPositions.merge(this.currentPosition, 1, Integer::sum);
                if(stopAtFirstLocationVisitiedTwice && this.visitedPositions.get(this.currentPosition) == 2) {
                    System.out.println("Part 2: " + (int) (Math.abs(currentPosition.getX()) + Math.abs(currentPosition.getY())));
                    System.exit(0);
                }
            }
        }
    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }
}
