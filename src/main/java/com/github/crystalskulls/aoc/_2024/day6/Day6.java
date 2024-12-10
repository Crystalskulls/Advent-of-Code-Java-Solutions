package com.github.crystalskulls.aoc._2024.day6;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day6 extends Puzzle {

    private final char OBSTRUCTION = '#';
    private Map<Vector2D, Position> labMap = new HashMap<>();
    private Guard guard;

    public Day6() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day6/input.txt";
    }

    @Override
    protected void solvePart1() {
        guard.predictRoute(labMap);
        System.out.println("Part 1: " + guard.positions.size());
    }

    @Override
    protected void solvePart2() {
        Set<Vector2D> obstrcutionPositions = new HashSet<>();
        List<String> lines = FileReader.readAllLines(inputFile);
        int columns = lines.toArray().length;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < columns; j++) {
                guard = new Guard(guard.startPosition);
                Vector2D currentVector = new Vector2D(j, i);
                Position currentPosition = labMap.get(currentVector);
                if(currentPosition.obstruction || currentVector == guard.startPosition) {
                    continue;
                }
                labMap.put(currentVector, new Position(currentVector, true));
                if(guard.predictRoute(labMap)) {
                    obstrcutionPositions.add(currentVector);
                }
                labMap.put(currentVector, currentPosition);
            }
        }
        System.out.println("Part 2: " + obstrcutionPositions.size());
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        parseLabMap(lines);
    }

    private void parseLabMap(List<String> lines) {
        labMap = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            char[] chars = lines.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                Vector2D vector = new Vector2D(j, i);
                Position position = new Position(vector, chars[j] == OBSTRUCTION);
                labMap.put(vector, position);
                if(chars[j] == '^') {
                    guard = new Guard(vector);
                }
            }
        }
    }

    record Position(Vector2D value, boolean obstruction){}

    private static class Guard {
        Queue<Vector2D> directions;
        Vector2D currentPosition;
        Set<Vector2D> positions;
        HashMap<Vector2D, HashMap<Vector2D, Integer>> obstructionHits;
        Vector2D startPosition;

        Guard(Vector2D startPosition) {
            directions = new LinkedList<Vector2D>();
            directions.add(new Vector2D(0, -1));
            directions.add(new Vector2D(1, 0));
            directions.add(new Vector2D(0, 1));
            directions.add(new Vector2D(-1, 0));
            positions = new HashSet<>();
            currentPosition = startPosition;
            positions.add(startPosition);
            obstructionHits = new HashMap<>();
            obstructionHits.put(new Vector2D(0, -1), new HashMap<>());
            obstructionHits.put(new Vector2D(1, 0), new HashMap<>());
            obstructionHits.put(new Vector2D(0, 1), new HashMap<>());
            obstructionHits.put(new Vector2D(-1, 0), new HashMap<>());
            this.startPosition = startPosition;
        }

        boolean predictRoute(Map<Vector2D, Position> labMap) {
            Vector2D direction = updateDirection();
            while(true) {
                Vector2D nextPosition = new Vector2D(currentPosition.getX(), currentPosition.getY()).add(direction);
                Position position = labMap.get(nextPosition);
                if(position == null) {
                    break;
                }
                if(position.obstruction) {
                    obstructionHits.get(direction).merge(nextPosition, 1, Integer::sum);
                    if(obstructionHits.get(direction).get(nextPosition) == 2) {
                        // loop
                        return true;
                    }
                    direction = updateDirection();
                    continue;
                }
                currentPosition = nextPosition;
                positions.add(currentPosition);
            }
            return false;
        }

        private Vector2D updateDirection() {
            Vector2D direction = directions.poll();
            directions.add(direction);
            return direction;
        }
    }
}