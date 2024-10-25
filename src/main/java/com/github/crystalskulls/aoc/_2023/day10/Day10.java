package com.github.crystalskulls.aoc._2023.day10;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;

public class Day10 extends Puzzle {

    private char[][] gridOfTiles;
    private Map<String, Pipe> coordinateToPipeMap = new HashMap<>();
    private String startCoordinate = "";

    public Day10() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day10/input.txt";
    }

    @Override
    protected void solvePart1() {
        Pipe startPipe = this.coordinateToPipeMap.get(this.startCoordinate);
        int i = 0;
        startPipe.setValue(i);
        List<Pipe> currentPipes = startPipe.getConnectedPipes();
        while(!currentPipes.stream().filter(pipe -> pipe.getValue() == null).toList().isEmpty()) {
            i++;
            List<Pipe> nextPipes = new ArrayList<>();
            for (Pipe currentPipe : currentPipes) {
               currentPipe.setValue(i);
               nextPipes.addAll(currentPipe.getConnectedPipes().stream().filter(pipe -> pipe.getValue() == null).toList());
            }
            currentPipes = nextPipes;
        }
        System.out.println("Part 1: " + i);
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        this.gridOfTiles = new char[lines.size()][lines.getFirst().toCharArray().length];
        for (int i = 0; i < lines.size(); i++) {
            char[] chars = lines.get(i).toCharArray();
            System.arraycopy(chars, 0, this.gridOfTiles[i], 0, chars.length);
        }
        this.createPipeMaze();
        this.connectPipes();
    }

    private void createPipeMaze() {
        for (int i = 0; i < this.gridOfTiles.length; i++) {
            for (int j = 0; j < this.gridOfTiles[i].length; j++) {
                char tile = this.gridOfTiles[i][j];
                if(tile == 'S') {
                    this.startCoordinate = i + ":" + j;
                }
                this.coordinateToPipeMap.put(i + ":" + j, new Pipe(tile));
            }
        }
    }

    private void connectPipes() {
        this.coordinateToPipeMap.forEach((coordinate, pipe) -> {
            char tile = pipe.getTile();
            if(tile == '.') {
                // skip
            } else {
                String[] coorinates = coordinate.split(":");
                int y = Integer.parseInt(coorinates[0]), x = Integer.parseInt(coorinates[1]);
                pipe.connectNorth(this.coordinateToPipeMap.get(y-1 + ":" + x));
                pipe.connectEast(this.coordinateToPipeMap.get(y + ":" + (x+1)));
                pipe.connectSouth(this.coordinateToPipeMap.get(y+1 + ":" + x));
                pipe.connectWest(this.coordinateToPipeMap.get(y + ":" + (x-1)));
            }
        });
    }
}
