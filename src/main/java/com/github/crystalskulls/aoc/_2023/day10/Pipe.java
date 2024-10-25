package com.github.crystalskulls.aoc._2023.day10;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Pipe {

    private static final Map<Character, Set<Directions>> directionsMap = Map.of(
            '|', Set.of(Directions.NORTH, Directions.SOUTH),
            '-', Set.of(Directions.EAST, Directions.WEST),
            'L', Set.of(Directions.NORTH, Directions.EAST),
            'J', Set.of(Directions.NORTH, Directions.WEST),
            '7', Set.of(Directions.SOUTH, Directions.WEST),
            'F', Set.of(Directions.EAST, Directions.SOUTH),
            '.', Set.of(),
            'S', Set.of(Directions.NORTH, Directions.EAST, Directions.SOUTH, Directions.WEST)
    );

    private Integer value;
    private char tile;
    private Set<Directions> connectedDirections;
    private List<Pipe> connectedPipes = new ArrayList<>();

    public Pipe(char tile) {
        this.tile = tile;
        this.connectedDirections = directionsMap.get(this.tile);
    }

    public void connectNorth(Pipe otherPipe) {
        if(otherPipe == null || !this.connectedDirections.contains(Directions.NORTH)) {
            return;
        }
        if(otherPipe.connectedDirections.contains(Directions.SOUTH)) {
            this.connectedPipes.add(otherPipe);
        }
    }

    public void connectEast(Pipe otherPipe) {
        if(otherPipe == null || !this.connectedDirections.contains(Directions.EAST)) {
            return;
        }
        if(otherPipe.connectedDirections.contains(Directions.WEST)) {
            this.connectedPipes.add(otherPipe);
        }
    }

    public void connectSouth(Pipe otherPipe) {
        if(otherPipe == null || !this.connectedDirections.contains(Directions.SOUTH)) {
            return;
        }
        if(otherPipe.connectedDirections.contains(Directions.NORTH)) {
            this.connectedPipes.add(otherPipe);
        }
    }

    public void connectWest(Pipe otherPipe) {
        if(otherPipe == null || !this.connectedDirections.contains(Directions.WEST)) {
            return;
        }
        if(otherPipe.connectedDirections.contains(Directions.EAST)) {
            this.connectedPipes.add(otherPipe);
        }
    }
}
