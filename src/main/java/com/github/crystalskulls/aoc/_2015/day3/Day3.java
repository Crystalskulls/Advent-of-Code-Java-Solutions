package com.github.crystalskulls.aoc._2015.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day3 extends Puzzle {

    private GridOfHouses gridOfHouses = new GridOfHouses();
    private char[] movementInstructions;
    private Map<Character, Vector2D> instructionVectorMap = Map.of(
            '^', new Vector2D(0, 1),
            '>', new Vector2D(1, 0),
            '<', new Vector2D(-1, 0),
            'v', new Vector2D(0, -1)
    );

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2015/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        for (char movementInstruction : movementInstructions) {
            gridOfHouses.updateSantasCurrentPosition(instructionVectorMap.get(movementInstruction));
        }
        System.out.println("Part 1: " + gridOfHouses.houses.size());
    }

    @Override
    protected void solvePart2() {
        gridOfHouses = new GridOfHouses();
        for (int i = 0; i < movementInstructions.length; i++) {
            Vector2D vector2D = instructionVectorMap.get(movementInstructions[i]);
            if(i % 2 == 0) {
                gridOfHouses.updateSantasCurrentPosition(vector2D);
                continue;
            }
            gridOfHouses.updateRoboSantasCurrentPosition(vector2D);
        }
        System.out.println("Part 2: " + gridOfHouses.houses.size());
    }

    @Override
    protected void readInputData(String inputFile) {
        movementInstructions = FileReader.readAllChars(inputFile);
    }

    private static class GridOfHouses {
        private final Set<Vector2D> houses = new HashSet<>();
        private Vector2D santasCurrentPosition = new Vector2D(0, 0);
        private Vector2D roboSantasCurrentPostion = new Vector2D(0 , 0);

        private GridOfHouses() {
            houses.add(santasCurrentPosition);
        }

        private void updateSantasCurrentPosition(Vector2D vector2D) {
            santasCurrentPosition = santasCurrentPosition.add(vector2D);
            houses.add(santasCurrentPosition);
        }

        private void updateRoboSantasCurrentPosition(Vector2D vector2D) {
            roboSantasCurrentPostion = roboSantasCurrentPostion.add(vector2D);
            houses.add(roboSantasCurrentPostion);
        }
    }
}