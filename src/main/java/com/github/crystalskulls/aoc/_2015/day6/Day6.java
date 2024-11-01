package com.github.crystalskulls.aoc._2015.day6;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.ToString;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day6 extends Puzzle {

    List<Instruction> instructions = new ArrayList<>();
    boolean[][] lights = new boolean[1000][1000];
    Light[][] lightsBrithness = new Light[1000][1000];

    public Day6() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2015/day6/input.txt";
    }

    @Override
    protected void solvePart1() {
        int count = 0;
        for (Instruction instruction : instructions) {
            Vector2D firstVector = instruction.coordinatePair.getKey();
            Vector2D secondVector = instruction.coordinatePair.getValue();
            for (int i = (int) firstVector.getX(); i <= secondVector.getX(); i++) {
                for (int j = (int) firstVector.getY(); j <= secondVector.getY(); j++) {
                    if(instruction.toggle) {
                        lights[i][j] = !lights[i][j];
                    } else {
                        lights[i][j] = instruction.action;
                    }
                }
            }
        }
        for (boolean[] light : lights) {
            for (boolean b : light) {
                if (b) {
                    count++;
                }
            }
        }
        System.out.println("Part 1: " + count);
    }

    @Override
    protected void solvePart2() {
        for (int i = 0; i < lightsBrithness.length; i++) {
            for (int i1 = 0; i1 < lightsBrithness[i].length; i1++) {
                lightsBrithness[i][i1] = new Light();
            }
        }
        for (Instruction instruction : instructions) {
            Vector2D firstVector = instruction.coordinatePair.getKey();
            Vector2D secondVector = instruction.coordinatePair.getValue();
            for (int i = (int) firstVector.getX(); i <= secondVector.getX(); i++) {
                for (int j = (int) firstVector.getY(); j <= secondVector.getY(); j++) {
                    if(instruction.toggle) {
                        lightsBrithness[i][j].brightness += 2;
                    } else if(instruction.action){
                        lightsBrithness[i][j].brightness++;
                    } else {
                        lightsBrithness[i][j].brightness = Math.max(0, lightsBrithness[i][j].brightness-1);
                    }
                }
            }
        }
        int sum = 0;
        for (Light[] brithness : lightsBrithness) {
            for (Light light : brithness) {
                sum += light.brightness;
            }
        }
        System.out.println("Part 2: " + sum);
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> instructionList = FileReader.readAllLines(inputFile);
        instructionList.forEach(instruction -> instructions.add(parseInstruction(instruction)));
    }

    private Instruction parseInstruction(String instruction) {
        Instruction instructionObject = new Instruction();
        boolean action = false;
        boolean toggle = false;
        String[] instructionSnippets = instruction.split(" through ");
        String[] secondCoordinates = instructionSnippets[1].split(",");
        Vector2D secondVector = new Vector2D(Double.parseDouble(secondCoordinates[0]), Double.parseDouble(secondCoordinates[1]));
        String firstInstructionSnippet = instructionSnippets[0];
        String[] firstCoordinates;
        if(firstInstructionSnippet.startsWith("turn on ")) {
            action = true;
            firstCoordinates = firstInstructionSnippet.replace("turn on ", "").split(",");
        } else if(firstInstructionSnippet.startsWith("turn off ")) {
            firstCoordinates = firstInstructionSnippet.replace("turn off ", "").split(",");
        } else {
            toggle = true;
            firstCoordinates = firstInstructionSnippet.replace("toggle ", "").split(",");
        }
        Vector2D firstVector = new Vector2D(Double.parseDouble(firstCoordinates[0]), Double.parseDouble(firstCoordinates[1]));
        instructionObject.action = action;
        instructionObject.toggle = toggle;
        instructionObject.coordinatePair = new MutablePair<>(firstVector, secondVector);
        return instructionObject;
    }

    @ToString
    private static class Instruction {

        private Pair<Vector2D, Vector2D> coordinatePair;
        private boolean action;
        private boolean toggle;
    }

    private static class Light {
        int brightness = 0;
    }
}