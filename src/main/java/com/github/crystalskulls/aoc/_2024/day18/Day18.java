package com.github.crystalskulls.aoc._2024.day18;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day18 extends Puzzle {

    Map<Vector2D, Memory> memorySpace;
    List<Vector2D> corruptedMemorySpaces = new ArrayList<>();

    public Day18() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day18/input.txt";
    }

    @Override
    protected void solvePart1() {
        initMemorySpace();
        setCorruptedMemory(1024);
        findShortestPath(new ArrayList(List.of(new Vector2D(0, 0))));
        //drawMemorySpace();
        System.out.println("Part 1: " + memorySpace.get(new Vector2D(70, 70)).distance);
    }

    @Override
    protected void solvePart2() {
        for (int i = 1024; i < corruptedMemorySpaces.size(); i++) {
            initMemorySpace();
            setCorruptedMemory(i);
            //drawMemorySpace();
            findShortestPath(new ArrayList(List.of(new Vector2D(0, 0))));
            //drawMemorySpace();
            if(memorySpace.get(new Vector2D(70, 70)).distance == null) {
                //drawMemorySpace();
                System.out.println("Part 2: " + corruptedMemorySpaces.get(i-1));
                break;
            }
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        for (String coordinates : FileReader.readAllLines(inputFile)) {
            corruptedMemorySpaces.add(parseVector(coordinates));
        }
    }

    private void findShortestPath(List<Vector2D> queue) {
        while(!queue.isEmpty()) {
            Vector2D memoryLocation = queue.removeFirst();
            Memory currentMemory = memorySpace.get(memoryLocation);
            List<Vector2D> adjacentMemoryLocations = getNextFreeMemoryLocations(memoryLocation);
            for (Vector2D adjacentMemoryLocation : adjacentMemoryLocations) {
                Memory nextMemory = memorySpace.get(adjacentMemoryLocation);
                if(nextMemory.distance == null) {
                    queue.add(adjacentMemoryLocation);
                    nextMemory.distance = currentMemory.distance + 1;
                }
            }
        }
    }

    List<Vector2D> getNextFreeMemoryLocations(Vector2D currentMemoryLocation) {
        List<Vector2D> nextFreeMemoryLocations = new ArrayList<>();
        Vector2D leftLocation = currentMemoryLocation.add(new Vector2D(-1, 0));
        if(memorySpace.get(leftLocation) != null && !memorySpace.get(leftLocation).coorupted) {
            nextFreeMemoryLocations.add(leftLocation);
        }
        Vector2D rightLocation = currentMemoryLocation.add(new Vector2D(1, 0));
        if(memorySpace.get(rightLocation) != null && !memorySpace.get(rightLocation).coorupted) {
            nextFreeMemoryLocations.add(rightLocation);
        }
        Vector2D upLocation = currentMemoryLocation.add(new Vector2D(0, -1));
        if(memorySpace.get(upLocation) != null && !memorySpace.get(upLocation).coorupted) {
            nextFreeMemoryLocations.add(upLocation);
        }
        Vector2D downLocation = currentMemoryLocation.add(new Vector2D(0, 1));
        if(memorySpace.get(downLocation) != null && !memorySpace.get(downLocation).coorupted) {
            nextFreeMemoryLocations.add(downLocation);
        }
        return nextFreeMemoryLocations;
    }

    private void setCorruptedMemory(int limit) {
        for (int i = 0; i < limit; i++) {
            memorySpace.get(corruptedMemorySpaces.get(i)).coorupted = true;
        }
    }

    private Vector2D parseVector(String s) {
        String[] coordinates = s.split(",");
        return new Vector2D(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
    }

    private void initMemorySpace() {
        memorySpace = new HashMap<>();
        for (int y = 0; y <= 70; y++) {
            for (int x = 0; x <= 70; x++) {
                memorySpace.put(new Vector2D(x, y), new Memory());
            }
        }
        memorySpace.get(new Vector2D(0, 0)).distance = 0;
    }

    private void drawMemorySpace() {
        for (int y = 0; y <= 70; y++) {
            for (int x = 0; x <= 70; x++) {
                Memory memory = memorySpace.get(new Vector2D(x, y));
                if(memory.coorupted) {
                    System.out.print('#');
                } else if(memory.distance == null) {
                    System.out.print('.');
                } else {
                    System.out.print(memory.distance);
                }
            }
            System.out.println();
        }
    }

    private class Memory {
        Integer distance;
        boolean coorupted;
    }
}