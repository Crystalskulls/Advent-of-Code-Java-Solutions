package com.github.crystalskulls.aoc._2023.day2;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day2 extends Puzzle {

    private final List<Game> games = new ArrayList<>();
    private int sum = 0;

    public Day2() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day2/input.txt";
    }

    @Override
    protected void solvePart1() {
        int redCubeLimit = 12, greenCubeLimit = 13, blueCubeLimit = 14;
        for (int i = 0; i < this.games.size(); i++) {
            boolean possibleGame = true;
            for (CubeSubset cubeSubset : this.games.get(i).getCubeSubsets()) {
                if(cubeSubset.getRed() > redCubeLimit || cubeSubset.getBlue() > blueCubeLimit || cubeSubset.getGreen() > greenCubeLimit) {
                    possibleGame = false;
                    break;
                }
            }
            if(possibleGame) {
                this.sum += i + 1;
            }
        }
        System.out.println("Part 1: " + this.sum);
    }

    @Override
    protected void solvePart2() {
        this.sum = 0;
        for (Game game : this.games) {
            int minRed = 0, minGreen = 0, minBlue = 0;
            for (CubeSubset cubeSubset : game.getCubeSubsets()) {
                minRed = Math.max(minRed, cubeSubset.getRed());
                minGreen = Math.max(minGreen, cubeSubset.getGreen());
                minBlue = Math.max(minBlue, cubeSubset.getBlue());
            }
            this.sum += minRed * minGreen * minBlue;
        }
        System.out.println("Part 2: " + this.sum);
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> games = FileReader.readAllLines(inputFile);

        games.forEach(game -> {
            String cubeSets = game.split(": ")[1];
            Game currentGame = new Game();
            for (String cubeSet : cubeSets.split("; ")) {
                String[] cubes = cubeSet.split(", ");
                currentGame.getCubeSubsets().add(new CubeSubset(cubes));
            }
            this.games.add(currentGame);
        });
    }
}
