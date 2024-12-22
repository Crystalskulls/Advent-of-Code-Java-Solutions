package com.github.crystalskulls.aoc._2024.day20;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.ToString;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day20 extends Puzzle {

    Map<Vector2D, Memory> memorySpace = new HashMap<>();
    Map<Vector2D, Integer> cheatPicosecondsMap = new HashMap<>();
    int picosecondsWithoutCheat;
    Memory startMemory;
    Memory endMemory;

    public Day20() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day20/input.txt";
    }

    @Override
    protected void solvePart1() {
        picosecondsWithoutCheat = findShortestPath(new ArrayList<>(List.of(startMemory)), null);
        System.out.println("Part 1: " + picosecondsWithoutCheat);
        System.out.println("cheat");
        //findShortestPath(new ArrayList<>(List.of(startMemory)), new Vector2D(0, 0));
        for (Vector2D cheat : cheatPicosecondsMap.keySet()) {
            findShortestPath(new ArrayList<>(List.of(startMemory)), cheat);
        }
        System.out.println(cheatPicosecondsMap.entrySet().stream().filter(vector2DIntegerEntry -> vector2DIntegerEntry.getValue() != null && vector2DIntegerEntry.getValue() >= 100).toList().size());

    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                char character = chars[x];
                Vector2D position = new Vector2D(x, y);
                Memory memory = new Memory(Type.fromCharacter(character), position);
                if(memory.type.equals(Type.START)) {
                    memory.picoseconds = 0;
                    startMemory = memory;
                } else if(memory.type.equals(Type.END)) {
                    endMemory = memory;
                } else if(memory.type.equals(Type.WALL)) {
                    cheatPicosecondsMap.put(position, null);
                }
                memorySpace.put(position, memory);
            }
        }
    }

    private Integer findShortestPath(List<Memory> queue, Vector2D cheat) {
        //System.out.println("cheat: " + cheat);
        while(!queue.isEmpty()) {
            Memory currentMemory = queue.removeFirst();
            //System.out.println(currentMemory);
            List<Memory> neighbors = getNextFreeMemoryLocations(currentMemory.position);
            //System.out.println(neighbors);
            if(currentMemory.position.equals(cheat) && neighbors.stream().allMatch(neighbor -> neighbor.type.equals(Type.WALL))) {
                // segmentation fault
                //System.out.println(neighbors);
                cheatPicosecondsMap.put(cheat, null);
                reset();
                return null;
            }
            for (Memory nextMemory : neighbors) {
                if(!nextMemory.type.equals(Type.WALL) || nextMemory.position.equals(cheat)) {
                    queue.add(nextMemory);
                    nextMemory.picoseconds = currentMemory.picoseconds + 1;
                }
            }
        }
        int picosendons = endMemory.picoseconds;
        reset();
        cheatPicosecondsMap.put(cheat, picosecondsWithoutCheat - picosendons);
        return picosendons;
    }

    void reset() {
        memorySpace.forEach((key, value) -> value.picoseconds = null);
        startMemory.picoseconds = 0;
    }

    List<Memory> getNextFreeMemoryLocations(Vector2D currentMemoryLocation) {
        List<Memory> neighbors = new ArrayList<>();
        neighbors.add(memorySpace.get(currentMemoryLocation.add(new Vector2D(-1, 0))));
        neighbors.add(memorySpace.get(currentMemoryLocation.add(new Vector2D(1, 0))));
        neighbors.add(memorySpace.get(currentMemoryLocation.add(new Vector2D(0, -1))));
        neighbors.add(memorySpace.get(currentMemoryLocation.add(new Vector2D(0, 1))));
        return neighbors.stream().filter(Objects::nonNull).filter(memory -> memory.picoseconds == null).toList();
    }

    @ToString
    private class Memory {
        Integer picoseconds;
        Type type;
        Vector2D position;
        
        Memory(Type type, Vector2D position) {
            this.type = type;
            this.position = position;
        }
    }
    
    private enum Type {
        FREE('.'), WALL('#'), START('S'), END('E');

        final char character;

        Type(char character) {
            this.character = character;
        }

        public static Type fromCharacter(char character) {
            for (Type type : Type.values()) {
                if (type.character == character) {
                    return type;
                }
            }
            return null;
        }
    }
}