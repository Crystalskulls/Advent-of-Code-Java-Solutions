package com.github.crystalskulls.aoc._2024.day16;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day16 extends Puzzle {

    Map<Vector2D, Tile> tiles = new HashMap<>();
    Tile startTile;
    Tile endTile;
    int mazeWidth;
    int mazeHeight;

    public Day16() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day16/input.txt";
    }

    @Override
    protected void solvePart1() {
        List<Tile> queue = new ArrayList<>();
        queue.add(startTile);
        solveMaze(queue);
        System.out.println("Part 1: " + endTile.score);
        draw();
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        mazeHeight = lines.size();
        mazeWidth = lines.getFirst().length();
        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                Vector2D currentVector = new Vector2D(x, y);
                Tile tile = new Tile(currentVector, Type.fromString(chars[x] + ""));
                if(tile.type.equals(Type.START)) {
                    tile.score = 0;
                    startTile = tile;
                } else if (tile.type.equals(Type.END)) {
                    endTile = tile;
                }
                tiles.put(currentVector, tile);
            }
        }
    }

    private void solveMaze(List<Tile> queue) {
        boolean end = false;
        while(!queue.isEmpty()) {
            Tile tile = queue.removeFirst();
            List<Tile> nextTiles = getNextFreeTiles(tile);
            for (Tile nextFreeTile : nextTiles) {
                queue.add(nextFreeTile);
                int tmpScore = tile.score + 1;
                if(nextFreeTile.position.getX() == tile.position.getX() && nextFreeTile.position.getY() < tile.position.getY()) {
                    nextFreeTile.direction = Direction.NORTH;
                } else if (nextFreeTile.position.getX() > tile.position.getX() && nextFreeTile.position.getY() == tile.position.getY()) {
                    nextFreeTile.direction = Direction.EAST;
                } else if (nextFreeTile.position.getX() == tile.position.getX() && nextFreeTile.position.getY() > tile.position.getY()) {
                    nextFreeTile.direction = Direction.SOUTH;
                } else if (nextFreeTile.position.getX() < tile.position.getX() && nextFreeTile.position.getY() == tile.position.getY()) {
                    nextFreeTile.direction = Direction.WEST;
                }
                if(!nextFreeTile.direction.equals(tile.direction)) {
                    tmpScore += 1000;
                }
                if(nextFreeTile.score == null || tmpScore <= nextFreeTile.score) {
                    nextFreeTile.score = tmpScore;
                }
                nextFreeTile.visitedBy.add(tile);
                if(nextFreeTile.type.equals(Type.END)) {
                    end = true;
                    break;
                }
            }
            if(end) {
                break;
            }
            queue.sort(Comparator.comparingInt(t -> t.score));
        }
    }

    private void draw() {
        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                Tile tile = tiles.get(new Vector2D(x, y));
                if(tile.type.equals(Type.FREE)) {
                    System.out.print(tile.direction.c);
                } else {
                    System.out.print(tiles.get(new Vector2D(x, y)).type.c);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static class Tile {
        private Vector2D position;
        private Type type;
        Integer score;
        Direction direction;
        List<Tile> visitedBy = new ArrayList<>();

        Tile(Vector2D position, Type type) {
            this.type = type;
            this.position = position;
            this.direction = Direction.NONE;
        }

    }

    List<Tile> getNextFreeTiles(Tile tile) {
        List<Tile> nextFreeTiles = new ArrayList<>();
        nextFreeTiles.add(tiles.get(tile.position.add(new Vector2D(-1, 0))));
        nextFreeTiles.add(tiles.get(tile.position.add(new Vector2D(1, 0))));
        nextFreeTiles.add(tiles.get(tile.position.add(new Vector2D(0, -1))));
        nextFreeTiles.add(tiles.get(tile.position.add(new Vector2D(0, 1))));
        return nextFreeTiles.stream().filter(t -> t.type.equals(Type.FREE) || t.type.equals(Type.END)).toList();
    }

    private enum Direction {
        NORTH("^"), EAST(">"), SOUTH("v"), WEST("<"), NONE(".");

        String c;
        Direction(String c) {
            this.c = c;
        }

        public static Type fromString(String text) {
            for (Type type : Type.values()) {
                if (type.c.equalsIgnoreCase(text)) {
                    return type;
                }
            }
            return null;
        }
    }

    private enum Type {
        WALL("#"), START("S"), END("E"), FREE(".");

        String c;
        Type(String c) {
            this.c = c;
        }

        public static Type fromString(String text) {
            for (Type type : Type.values()) {
                if (type.c.equalsIgnoreCase(text)) {
                    return type;
                }
            }
            return null;
        }
    }
}