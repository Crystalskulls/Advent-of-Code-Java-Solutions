package com.github.crystalskulls.aoc._2017.day3;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.Arrays;

public class Day3 extends Puzzle {

    private int inputNumber;

    public Day3() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2017/day3/input.txt";
    }

    @Override
    protected void solvePart1() {
        double base = 1.;
        int corner;
        int ring = (int) (Math.sqrt(inputNumber) / 2 + 1);
        int largestCorner = (int) Math.pow(ring, 2.);
        while(true) {
            ring++;
            corner = (int)Math.pow(base, 2.);
            if(corner >= inputNumber) {
                break;
            }
            base += 2;
        }
        int dist = ((int)base - 1);
        int[] corners = new int[]{corner, corner - dist, corner - (dist * 2), corner - (dist * 3)};
        for (int j = 0; j < corners.length; j++) {
            if(j == corners.length - 1) {
                int m = corners[j] - (dist / 2);
                System.out.println("Part 1: " + (Math.abs(inputNumber-m) + ring - 1));
                break;
            } else if(inputNumber <= corners[j] && inputNumber >= corners[j+1]) {
                int m = corners[j] - (dist / 2);
                System.out.println("Part 1: " + (Math.abs(inputNumber-m) + ring - 1));
                break;
            }
        }
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        inputNumber = Integer.parseInt(FileReader.read(inputFile));
    }

}
