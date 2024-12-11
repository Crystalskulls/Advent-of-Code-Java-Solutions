package com.github.crystalskulls.aoc._2024.day10;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day10 extends Puzzle {

    List<Trailhead> trailheads = new ArrayList<>();
    static Map<Vector2D, Position> topographicMap = new HashMap<>();

    public Day10() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day10/input.txt";
    }

    @Override
    protected void solvePart1() {
        trailheads.forEach(Trailhead::calculateScore);
        System.out.println("Part 1: " + trailheads.stream().mapToInt(trailhead -> trailhead.score).sum());
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: " + trailheads.stream().mapToInt(trailhead -> trailhead.rating).sum());
    }

    @Override
    protected void readInputData(String inputFile) {
        parseTopographicMap(inputFile);
    }

    private void parseTopographicMap(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                int height = Integer.parseInt(String.valueOf(line.charAt(x)));
                Vector2D vector = new Vector2D(x, y);
                Position position = new Position(vector, height);
                topographicMap.put(vector, position);
                if(height == 0) {
                    trailheads.add(new Trailhead(position, 0, 0));
                }
            }
        }
    }

    private static List<Position> getNeighbors(Position position) {
        List<Position> neighbors = new ArrayList<>();
        neighbors.add(topographicMap.get(position.vector.add(new Vector2D(-1, 0))));
        neighbors.add(topographicMap.get(position.vector.add(new Vector2D(1, 0))));
        neighbors.add(topographicMap.get(position.vector.add(new Vector2D(0, -1))));
        neighbors.add(topographicMap.get(position.vector.add(new Vector2D(0, 1))));
        return neighbors.stream().filter(Objects::nonNull).toList();
    }

    @AllArgsConstructor
    private static class Trailhead {
        Position position;
        int score;
        int rating;

        void calculateScore() {
            List<Position> positions = new ArrayList<>();
            positions.add(this.position);
            HashSet<Position> ninePositions = new HashSet<>();

            while(!positions.isEmpty()) {
                Position currentPosition = positions.removeFirst();
                if(currentPosition.height == 9) {
                    rating++;
                    ninePositions.add(currentPosition);
                }
                List<Position> neighbors = getNeighbors(currentPosition);
                for (Position neighbor : neighbors) {
                    if(neighbor.height == currentPosition.height + 1) {
                        positions.add(neighbor);
                    }
                }
            }
            this.score = ninePositions.size();
        }
    }
    record Position(Vector2D vector, int height){};
}