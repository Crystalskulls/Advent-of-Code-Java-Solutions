package com.github.crystalskulls.aoc._2018.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day3 extends Puzzle {

    List<Claim> claims = new ArrayList<>();
    Map<Vector2D, Integer> coordinateCounterMap = new HashMap<>();
    Set<Vector2D> coordinatesWithinTwoOrMoreClaims = new HashSet<>();
    Map<Integer, Set<Vector2D>> claimCoordinatesMap = new HashMap<>();

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2018/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        for (Claim claim : claims) {
            Set<Vector2D> claimCoordinates = new HashSet<>();
            for (int x = claim.inchesFromLeftEdge; x < claim.inchesFromLeftEdge + claim.width; x++) {
                for (int y = claim.inchesFromTopEdge; y < claim.inchesFromTopEdge + claim.heigh; y++) {
                    Vector2D coordinate = new Vector2D(x, y);
                    claimCoordinates.add(coordinate);
                    int c = coordinateCounterMap.compute(coordinate, (k, v) -> v == null ? 1 : v + 1);
                    if(c > 1) {
                        coordinatesWithinTwoOrMoreClaims.add(coordinate);
                    }
                }
            }
            claimCoordinatesMap.put(claim.id, claimCoordinates);
        }
        System.out.println("Part 1: " + coordinatesWithinTwoOrMoreClaims.size());
    }

    @Override
    protected void solvePart2() {
        for (Integer id : claimCoordinatesMap.keySet()) {
            Set<Vector2D> intersectionSet = new HashSet<>(claimCoordinatesMap.get(id));
            intersectionSet.retainAll(coordinatesWithinTwoOrMoreClaims);
            if(intersectionSet.isEmpty()) {
                System.out.println("Part 2: " + id);
                break;
            }
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        FileReader.readAllLines(inputFile)
                .forEach(claim -> {
                    String[] claimSnippets = claim.split(" ");
                    int id = Integer.parseInt(claimSnippets[0].substring(1));
                    String[] inchesSnippets = claimSnippets[2].split(",");
                    int inchesFromLeftEdge = Integer.parseInt(inchesSnippets[0]);
                    int inchesFromTopEdge = Integer.parseInt(inchesSnippets[1].substring(0, inchesSnippets[1].length() - 1));
                    String[] widthAndHeigh = claimSnippets[3].split("x");
                    int width = Integer.parseInt(widthAndHeigh[0]);
                    int heigh = Integer.parseInt(widthAndHeigh[1]);
                    claims.add(new Claim(
                            id,
                            inchesFromLeftEdge,
                            inchesFromTopEdge,
                            width,
                            heigh
                    ));
                });
    }

    record Claim(int id, int inchesFromLeftEdge, int inchesFromTopEdge, int width, int heigh) {}
}
