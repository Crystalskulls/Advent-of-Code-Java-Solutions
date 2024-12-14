package com.github.crystalskulls.aoc._2024.day14;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 extends Puzzle {

    Pattern numberPattern = Pattern.compile("-*\\d+");
    List<Robot> robots = new ArrayList<>();
    Area areaOutsideBathroom;

    public Day14() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day14/input.txt";
    }

    @Override
    protected void solvePart1() {
        areaOutsideBathroom = new Area(robots, 101.0, 103.0);
        areaOutsideBathroom.determineSafestArea();
        System.out.println("Part 1: " + areaOutsideBathroom.safetyFactor);
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2: After 6752 seconds");
        areaOutsideBathroom.draw();
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> lines = FileReader.readAllLines(inputFile);
        for (String line : lines) {
            String[] snippets = line.split(" ");
            String positionString = snippets[0];
            String velocitiyString = snippets[1];
            robots.add(new Robot(new Vector2D(parseX(positionString), parseY(positionString)), new Vector2D(parseX(velocitiyString), parseY(velocitiyString))));
        }
    }

    private double parseX(String s) {
        Matcher numberMatcher = numberPattern.matcher(s);
        numberMatcher.find();
        return Double.parseDouble(numberMatcher.group());
    }

    private double parseY(String s) {
        Matcher numberMatcher = numberPattern.matcher(s);
        numberMatcher.find();
        numberMatcher.find();
        return Double.parseDouble(numberMatcher.group());
    }

    private static class Area {
        private List<Robot> robots;
        double width;
        double height;
        static int quadrantWidth;
        static int quadrantHeight;
        int safetyFactor = 0;

        private Area(List<Robot> robots, double width, double height) {
            this.robots = robots;
            this.width = width;
            this.height = height;
        }

        private void determineSafestArea() {
            for (int i = 0; i <= 6751; i++) {
                robots.forEach(robot -> robot.move(width, height));
                if(i == 99) {
                    calculateSafetyFactor();
                }
            }
        }

        private void draw() {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int x = j;
                    int y = i;
                    boolean anyMatch = robots.stream().anyMatch(robot -> robot.position.equals(new Vector2D(x, y)));
                    System.out.print(anyMatch ? "*" : ".");
                }
                System.out.println();
            }
            System.out.println();
        }

        private void calculateSafetyFactor() {
            quadrantWidth = (int) (width / 2);
            quadrantHeight = (int) (height / 2);

            int countRobotsTopLeftQuadrant = countRobots(0, 0, quadrantWidth, quadrantHeight);
            int countRobotsTopRightQuadrant = countRobots((int) (width - quadrantWidth), 0, (int) width, quadrantHeight);
            int countRobotsDownLeftQuadrant = countRobots(0, (int) (height - quadrantHeight), quadrantWidth, (int) height);
            int countRobotsDownRightQuadrant = countRobots((int) (width - quadrantWidth), (int) (height - quadrantHeight), (int) width, (int) height);
            safetyFactor = countRobotsTopLeftQuadrant * countRobotsTopRightQuadrant * countRobotsDownLeftQuadrant * countRobotsDownRightQuadrant;
        }

        private int countRobots(int startX, int startY, int endX, int endY) {
            int count = 0;
            for (int i = startX; i < endX; i++) {
                for (int j = startY; j < endY; j++) {
                    Vector2D vector = new Vector2D(i, j);
                    count += robots.stream().filter(robot -> robot.position.equals(vector)).toList().size();
                }
            }
            return count;
        }

    }

    @AllArgsConstructor
    private static class Robot {
        private Vector2D position;
        private Vector2D velocitiy;

        @Override
        public String toString() {
            return "p=" + position.getX() + "," + position.getY() + " v=" + velocitiy.getX() + "," + velocitiy.getY() + "\n";
        }

        public void move(double width, double height) {
            Vector2D newPosition = position.add(velocitiy);
            if(newPosition.getX() < 0) {
                newPosition = newPosition.add(new Vector2D(width, 0));
            }
            if(newPosition.getX() >= width) {
                newPosition = newPosition.add(new Vector2D(-width, 0));
            }
            if(newPosition.getY() < 0) {
                newPosition = newPosition.add(new Vector2D(0, height));
            }
            if(newPosition.getY() >= height) {
                newPosition = newPosition.add(new Vector2D(0, -height));
            }
            position = newPosition;
        }
    }
}