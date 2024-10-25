package com.github.crystalskulls.aoc._2023.day14;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;

public class Day14 extends Puzzle {

    private Map<Integer, List<Character>> columns = new HashMap<>();
    private Map<Integer, List<Character>> firstColumns = new HashMap<>();

    private Map<Integer, List<Character>> rows = new HashMap<>();

    public Day14() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day14/input.txt";
    }

    @Override
    protected void solvePart1() {
        this.firstColumns = columns;
        this.columns = this.northTilt();
        this.columnsToRows();
        //this.printRows();
        int sum = 0;
        for (Integer column : this.columns.keySet()) {
            List<Character> characters = this.columns.get(column);
            for (int i = 0; i < characters.size(); i++) {
                sum += isRoundedRock(characters.get(i)) ? characters.size() - i : 0;
            }
        }
        System.out.println("Part 1: " + sum + "\n");
    }

    @Override
    protected void solvePart2() {
        for (int i = 1; i <= 1000000000; i++) {
            System.out.println(i);
            if(i > 1) {
                this.columns = this.northTilt();
                this.columnsToRows();
            }
            this.rows = this.westTilt();
            this.rowsToColumns();
            this.columns = this.southTilt();
            this.columnsToRows();
            this.rows = this.eastTilt();
            this.rowsToColumns();
        }

        int sum = 0;
        for (Integer column : this.columns.keySet()) {
            List<Character> characters = this.columns.get(column);
            for (int i = 0; i < characters.size(); i++) {
                sum += isRoundedRock(characters.get(i)) ? characters.size() - i : 0;
            }
        }
        System.out.println("Part 2: " + sum + "\n");
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> rows = FileReader.readAllLines(inputFile);
        for (int i = 0; i < rows.size(); i++) {
            char[] characters = rows.get(i).toCharArray();
            for (int j = 0; j < characters.length; j++) {
                if(i == 0) {
                    this.columns.put(j, new ArrayList<>());
                }
                this.columns.get(j).add(characters[j]);
            }
        }
    }

    private Map<Integer, List<Character>> northTilt() {
        Map<Integer, List<Character>> newColumns = new HashMap<>();
        for (Integer column : this.columns.keySet()) {
            Integer lastEmptySpaceIndex = null;
            Integer lastCubeShapedRockIndex = null;
            Integer lastRoundedRockIndex = null;
            List<Character> newColumn = new ArrayList<>();
            List<Character> characters = this.columns.get(column);
            for (int i = 0; i < characters.size(); i++) {
                Character character = characters.get(i);
                newColumn.add(character);
                if(isEmptySpace(character)) {
                    lastEmptySpaceIndex = lastEmptySpaceIndex == null ? i : lastEmptySpaceIndex;
                } else if(isCubeShapedRock(character)) {
                    lastCubeShapedRockIndex = i;
                    lastEmptySpaceIndex = null;
                } else {
                    lastRoundedRockIndex = i;
                }

                if(lastRoundedRockIndex != null && lastEmptySpaceIndex != null) {
                    if(lastCubeShapedRockIndex == null || lastEmptySpaceIndex > lastCubeShapedRockIndex) {
                        newColumn.set(lastEmptySpaceIndex, 'O');
                        newColumn.set(lastRoundedRockIndex, '.');
                        lastEmptySpaceIndex = lastEmptySpaceIndex + newColumn.subList(lastEmptySpaceIndex, newColumn.size()).indexOf('.');
                    }
                }
                lastRoundedRockIndex = null;
            }
            newColumns.put(column, newColumn);
        }
        return newColumns;
    }

    private Map<Integer, List<Character>> westTilt() {
        Map<Integer, List<Character>> newRows = new HashMap<>();
        for (Integer row : this.rows.keySet()) {
            Integer lastEmptySpaceIndex = null;
            Integer lastCubeShapedRockIndex = null;
            Integer lastRoundedRockIndex = null;
            List<Character> newRow = new ArrayList<>();
            List<Character> characters = this.rows.get(row);
            for (int i = 0; i < characters.size(); i++) {
                Character character = characters.get(i);
                newRow.add(character);
                if(isEmptySpace(character)) {
                    lastEmptySpaceIndex = lastEmptySpaceIndex == null ? i : lastEmptySpaceIndex;
                } else if(isCubeShapedRock(character)) {
                    lastCubeShapedRockIndex = i;
                    lastEmptySpaceIndex = null;
                } else {
                    lastRoundedRockIndex = i;
                }

                if(lastRoundedRockIndex != null && lastEmptySpaceIndex != null) {
                    if(lastCubeShapedRockIndex == null || lastEmptySpaceIndex > lastCubeShapedRockIndex) {
                        newRow.set(lastEmptySpaceIndex, 'O');
                        newRow.set(lastRoundedRockIndex, '.');
                        lastEmptySpaceIndex = lastEmptySpaceIndex + newRow.subList(lastEmptySpaceIndex, newRow.size()).indexOf('.');
                    }
                }
                lastRoundedRockIndex = null;
            }
            newRows.put(row, newRow);
        }
        return newRows;
    }

    private Map<Integer, List<Character>> southTilt() {
        Map<Integer, List<Character>> newColumns = new HashMap<>();
        for (Integer column : this.columns.keySet()) {
            Integer lastEmptySpaceIndex = null;
            Integer lastCubeShapedRockIndex = null;
            Integer lastRoundedRockIndex = null;
            List<Character> newColumn = new ArrayList<>();
            List<Character> characters = this.columns.get(column);
            for (int i = characters.size() -1; i >= 0; i--) {
                Character character = characters.get(i);
                newColumn.add(character);
                if(isEmptySpace(character)) {
                    lastEmptySpaceIndex = lastEmptySpaceIndex == null ? characters.size() - 1 - i : lastEmptySpaceIndex;
                } else if(isCubeShapedRock(character)) {
                    lastCubeShapedRockIndex = characters.size() - 1 - i;
                    lastEmptySpaceIndex = null;
                } else {
                    lastRoundedRockIndex = characters.size() - 1 - i;
                }

                if(lastRoundedRockIndex != null && lastEmptySpaceIndex != null) {
                    if(lastCubeShapedRockIndex == null || lastEmptySpaceIndex > lastCubeShapedRockIndex) {
                        newColumn.set(lastEmptySpaceIndex, 'O');
                        newColumn.set(lastRoundedRockIndex, '.');
                        lastEmptySpaceIndex = lastEmptySpaceIndex + newColumn.subList(lastEmptySpaceIndex, newColumn.size()).indexOf('.');
                    }
                }
                lastRoundedRockIndex = null;
            }
            newColumns.put(column, newColumn.reversed());
        }
        return newColumns;
    }

    private Map<Integer, List<Character>> eastTilt() {
        Map<Integer, List<Character>> newRows = new HashMap<>();
        for (Integer row : this.rows.keySet()) {
            Integer lastEmptySpaceIndex = null;
            Integer lastCubeShapedRockIndex = null;
            Integer lastRoundedRockIndex = null;
            List<Character> newRow = new ArrayList<>();
            List<Character> characters = this.rows.get(row);
            for (int i = characters.size() - 1; i >= 0; i--) {
                Character character = characters.get(i);
                newRow.add(character);
                if(isEmptySpace(character)) {
                    lastEmptySpaceIndex = lastEmptySpaceIndex == null ? characters.size() - 1 - i : lastEmptySpaceIndex;
                } else if(isCubeShapedRock(character)) {
                    lastCubeShapedRockIndex = characters.size() - 1 - i;
                    lastEmptySpaceIndex = null;
                } else {
                    lastRoundedRockIndex = characters.size() - 1 - i;
                }

                if(lastRoundedRockIndex != null && lastEmptySpaceIndex != null) {
                    if(lastCubeShapedRockIndex == null || lastEmptySpaceIndex > lastCubeShapedRockIndex) {
                        newRow.set(lastEmptySpaceIndex, 'O');
                        newRow.set(lastRoundedRockIndex, '.');
                        lastEmptySpaceIndex = lastEmptySpaceIndex + newRow.subList(lastEmptySpaceIndex, newRow.size()).indexOf('.');
                    }
                }
                lastRoundedRockIndex = null;
            }
            newRows.put(row, newRow.reversed());
        }
        return newRows;
    }

    private void columnsToRows() {
        for (Integer column : this.columns.keySet()) {
            List<Character> characters = this.columns.get(column);
            for (int i = 0; i < characters.size(); i++) {
                if(column == 0) {
                    this.rows.put(i, new ArrayList<>());
                }
                this.rows.get(i).add(characters.get(i));
            }
        }
    }

    private void rowsToColumns() {
        for (Integer row : this.rows.keySet()) {
            List<Character> characters = this.rows.get(row);
            for (int i = 0; i < characters.size(); i++) {
                if(row == 0) {
                    this.columns.put(i, new ArrayList<>());
                }
                this.columns.get(i).add(characters.get(i));
            }
        }
    }

    private void printRows() {
        for (Integer row : this.rows.keySet()) {
            for (Character character : this.rows.get(row)) {
                System.out.print(character);
            }
            System.out.println();
        }
    }

    private void printColumns() {
        for (Integer column : this.columns.keySet()) {
            for (Character character : this.columns.get(column)) {
                System.out.print(character);
            }
            System.out.println();
        }
    }

    private boolean isEmptySpace(Character character) {
        return character == '.';
    }

    private boolean isCubeShapedRock(Character character) {
        return character == '#';
    }

    private boolean isRoundedRock(Character character) {

        return character == 'O';
    }
}
