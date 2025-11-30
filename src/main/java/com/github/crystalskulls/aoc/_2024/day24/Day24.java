package com.github.crystalskulls.aoc._2024.day24;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Day24 extends Puzzle {

    HashMap<String, Wire> wires = new HashMap<>();
    List<Gate> gates = new ArrayList<>();

     public Day24() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day24/input.txt";
    }

    @Override
    protected void solvePart1() {
        while(true) {
            boolean done = true;
            for (Gate gate : gates) {
             if(!gate.writeOutput()) {
                 done = false;
             }
            }
            if(done) {
             break;
            }
        }

        gates.sort(Comparator.comparing(o -> o.targetWire.name));
        String binaryNumber = "";
        for (int i = gates.size() -1; i >= 0; i--) {
            if(!gates.get(i).targetWire.name.startsWith("z")) {
                break;
            }
            binaryNumber += String.valueOf(gates.get(i).targetWire.value);
        }
        System.out.println("Part 1: " + Long.parseLong(binaryNumber, 2));
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> input = FileReader.readAllLines(inputFile, "\n\n");
        parseWires(input.getFirst());
        parseGates(input.getLast());
    }

    private void parseWires(String wires) {
         String[] nameAndValues = wires.split("\n");
         for (String nameAndValue : nameAndValues) {
             String[] snippets = nameAndValue.split(": ");
             this.wires.put(snippets[0], new Wire(snippets[0], Integer.parseInt(snippets[1])));
         }
    }
    
    private void parseGates(String gates) {
         String[] gatesAndWires = gates.split("\n");
         for (String gateAndWire : gatesAndWires) {
             String[] snippets = gateAndWire.split(" ");
             wires.putIfAbsent(snippets[0], new Wire(snippets[0], null));
             wires.putIfAbsent(snippets[2], new Wire(snippets[2], null));
             wires.putIfAbsent(snippets[4], new Wire(snippets[4], null));
             this.gates.add(new Gate(wires.get(snippets[0]), wires.get(snippets[2]), wires.get(snippets[4]), Type.fromString(snippets[1])));
         }
    }

    @AllArgsConstructor
    @ToString
    private class Wire {
         String name;
         Integer value;
    }

    @AllArgsConstructor
    @ToString
    private static class Gate {
         Wire leftWire;
         Wire rightWire;
         Wire targetWire;
         Type type;

         boolean writeOutput() {
             if(leftWire.value == null || rightWire.value == null) {
                 return false;
             }
             switch (type) {
                 case AND -> targetWire.value = leftWire.value & rightWire.value;
                 case OR -> targetWire.value = leftWire.value | rightWire.value;
                 case XOR -> targetWire.value = leftWire.value ^ rightWire.value;
             }
             return true;
         }
    }

    private enum Type {
         AND("AND"), OR("OR"), XOR("XOR");

        final String string;

        Type(String string) {
            this.string = string;
        }

        public static Type fromString(String string) {
            for (Type type : Type.values()) {
                if (type.string.equals(string)) {
                    return type;
                }
            }
            return null;
        }
    }
}