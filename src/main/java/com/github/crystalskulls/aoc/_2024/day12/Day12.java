package com.github.crystalskulls.aoc._2024.day12;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class Day12 extends Puzzle {

    Map<Vector2D, Plant> plants = new HashMap<>();
    Map<Character, List<HashSet<Plant>>> regionMap = new HashMap<>();
    int width;
    int height;

    public Day12() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day12/input.txt";
    }

    @Override
    protected void solvePart1() {
        //draw();
        determineRegion();
        //System.out.println(regionMap);
        int result = 0;
        for (Character c : regionMap.keySet()) {
            for (HashSet<Plant> region : regionMap.get(c)) {
                int area = region.size();
                int perimeter = region.stream().mapToInt(plant -> plant.perimeter).sum();
                //System.out.println("A region of " + c + " plants with price " + area + " * " + perimeter);
                result += region.size() * region.stream().mapToInt(plant -> plant.perimeter).sum();
            }
        }
        System.out.println("Part 1: " + result);
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        height = lines.size();
        width = lines.getFirst().length();
        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                regionMap.put(chars[x], new ArrayList<>());
                Vector2D currentVector = new Vector2D(x, y);
                plants.put(currentVector, new Plant(chars[x], currentVector));
            }
        }
        for (Vector2D vector2D : plants.keySet()) {
            Plant plant = plants.get(vector2D);
            plant.determineNeighbors();
        }
    }

    private void determineRegion() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //System.out.println(x + "," + y);
                Plant currentPlant = plants.get(new Vector2D(x, y));
                if(currentPlant.hasRegion) {
                    continue;
                }
                addToRegion(currentPlant);
            }
        }
    }

    private void addToRegion(Plant plant) {
        //System.out.println("plant: " + plant);
        List<Plant> queue = new ArrayList<>();
        queue.add(plant);
        HashSet<Plant> newRegion = new HashSet<>();
        while(!queue.isEmpty()) {
            //System.out.println(queue.size());
            Plant currentPlant = queue.removeFirst();
            if(currentPlant.hasRegion) {
                continue;
            }
            if(currentPlant.type.equals(plant.type)) {
                currentPlant.hasRegion = true;
                newRegion.add(currentPlant);
                queue.addAll(getAdjecentPlants(currentPlant));
            }
        }
        regionMap.get(plant.type).add(newRegion);
    }

    private List<Plant> getAdjecentPlants(Plant currentPlant) {
        List<Plant> adjecentPlants = new ArrayList<>();
        adjecentPlants.add(plants.get(currentPlant.position.add(new Vector2D(-1, 0))));
        adjecentPlants.add(plants.get(currentPlant.position.add(new Vector2D(0, -1))));
        adjecentPlants.add(plants.get(currentPlant.position.add(new Vector2D(1, 0))));
        adjecentPlants.add(plants.get(currentPlant.position.add(new Vector2D(0, 1))));
        return adjecentPlants.stream().filter(Objects::nonNull).filter(plant -> !plant.hasRegion).filter(plant -> plant.type.equals(currentPlant.type)).toList();
    }

    private class Plant {
        Character type;
        int perimeter;
        Vector2D position;
        List<Plant> neighbors;
        boolean hasRegion;

        Plant(Character type, Vector2D position) {
            this.type = type;
            this.position = position;
        }

        public void determineNeighbors() {
            this.neighbors = getAdjecentPlants(this);
            this.perimeter += 4 - neighbors.size();
            for (Plant neighbor : neighbors) {
                if(!neighbor.type.equals(this.type)) {
                    this.perimeter++;
                }
            }
        }

        @Override
        public String toString() {
            return "Plant{" +
                    "type=" + type +
                    ", perimeter=" + perimeter +
                    ", position=" + position +
                    '}';
        }
    }

    private void draw() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Plant plant = plants.get(new Vector2D(x, y));
                System.out.print(plant.type);
            }
            System.out.println();
        }
    }
}