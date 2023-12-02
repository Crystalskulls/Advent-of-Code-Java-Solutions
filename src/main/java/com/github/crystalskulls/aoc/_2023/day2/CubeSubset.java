package com.github.crystalskulls.aoc._2023.day2;

import lombok.Data;

import java.util.Objects;

@Data
public class CubeSubset {

    private int red;
    private int green;
    private int blue;

    public CubeSubset(String[] cubes) {
        for (String cube : cubes) {
            String[] snippets = cube.split(" ");
            int numberOfCubes = Integer.parseInt(snippets[0]);
            String color = snippets[1];
            if(Objects.equals(color, "red")) {
                this.setRed(numberOfCubes);
            } else if(Objects.equals(color, "green")) {
                this.setGreen(numberOfCubes);
            } else {
                this.setBlue(numberOfCubes);
            }
        }
    }
}
