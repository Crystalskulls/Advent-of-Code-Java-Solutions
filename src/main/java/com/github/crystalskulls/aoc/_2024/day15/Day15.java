package com.github.crystalskulls.aoc._2024.day15;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day15 extends Puzzle {

    private Warehouse warehouse;
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";

    public Day15() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day15/input.txt";
    }

    @Override
    protected void solvePart1() {
        warehouse.moveRobot();
        System.out.println("Part 1: " + warehouse.warehouseObjects.entrySet().stream().filter(vector2DWarehousObjectEntry -> vector2DWarehousObjectEntry.getValue().equals(WarehousObject.BOX)).mapToInt(entry -> (int) (100 * entry.getKey().getY() + entry.getKey().getX())).sum());
    }

    @Override
    protected void solvePart2() {
        System.out.println("Part 2:");
        readInputDataForPart2();
        warehouse.moveRobot2();
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> mapAndMovements = FileReader.readAllLines(inputFile, "\n\n");
        String[] lines = mapAndMovements.getFirst().split("\n");
        Map<Vector2D, WarehousObject> warehouseObjecs = new HashMap<>();
        Queue<Character> movements = mapAndMovements.getLast().replace("\n", "").chars().mapToObj(c -> (char)c).collect(Collectors.toCollection(LinkedList::new));
        Robot robot = new Robot();
        for (int y = 0; y < lines.length; y++) {
            char[] chars = lines[y].toCharArray();
            for (int x = 0; x < chars.length; x++) {
                if(chars[x] == '@') {
                    robot.position = new Vector2D(x, y);
                } else {
                    warehouseObjecs.put(new Vector2D(x, y), chars[x] == '#' ? WarehousObject.WALL : chars[x] == '.' ? WarehousObject.FREE_SPACE : WarehousObject.BOX);
                }
            }
        }
        warehouse = new Warehouse(robot, movements, warehouseObjecs, new ArrayList<>(), lines.length, lines[0].length());
    }

    void readInputDataForPart2() {
        List<String> mapAndMovements = FileReader.readAllLines(inputFile, "\n\n");
        String[] lines = mapAndMovements.getFirst().split("\n");
        Map<Vector2D, WarehousObject> warehouseObjecs = new HashMap<>();
        Queue<Character> movements = mapAndMovements.getLast().replace("\n", "").chars().mapToObj(c -> (char)c).collect(Collectors.toCollection(LinkedList::new));
        Robot robot = new Robot();
        List<Box> boxes = new ArrayList<>();
        for (int y = 0; y < lines.length; y++) {
            char[] chars = lines[y].toCharArray();
            int currentIndex = 0;
            for (int x = 0; x < chars.length; x++) {
                currentIndex = 2 * x;
                if(chars[x] == '@') {
                    robot.position = new Vector2D(currentIndex, y);
                    currentIndex++;
                    warehouseObjecs.put(new Vector2D(currentIndex, y), WarehousObject.FREE_SPACE);
                } else {
                    if(chars[x] == '#') {
                        warehouseObjecs.put(new Vector2D(currentIndex, y), WarehousObject.WALL);
                        currentIndex++;
                        warehouseObjecs.put(new Vector2D(currentIndex, y), WarehousObject.WALL);
                    } else if (chars[x] == '.') {
                        warehouseObjecs.put(new Vector2D(currentIndex, y), WarehousObject.FREE_SPACE);
                        currentIndex++;
                        warehouseObjecs.put(new Vector2D(currentIndex, y), WarehousObject.FREE_SPACE);
                    } else {
                        Box box = new Box();
                        warehouseObjecs.put(new Vector2D(currentIndex, y), WarehousObject.BOX);
                        box.leftVector = new Vector2D(currentIndex, y);
                        currentIndex++;
                        warehouseObjecs.put(new Vector2D(currentIndex, y), WarehousObject.BOX);
                        box.rightVector = new Vector2D(currentIndex, y);
                    }
                }
            }
        }
        warehouse = new Warehouse(robot, movements, warehouseObjecs, boxes, lines.length, lines[0].length());
    }

    @AllArgsConstructor
    private static class Warehouse {
        private Robot robot;
        private Queue<Character> movements;
        private Map<Vector2D, WarehousObject> warehouseObjects;
        private List<Box> boxes;
        private int height;
        private int width;

        private void moveRobot() {
            //System.out.println("Initial state:");
            //draw();
            while (!movements.isEmpty()) {
                Character nextMovement = movements.poll();
                //System.out.println("Move " + nextMovement + ":");
                Vector2D movementVector = getNextRobotPosition(nextMovement);
                Vector2D possibleNextRobotPosition = robot.position.add(movementVector);
                WarehousObject warehousObject = warehouseObjects.get(possibleNextRobotPosition);
                if (warehousObject != null && warehousObject.equals(WarehousObject.FREE_SPACE)) {
                    warehouseObjects.put(robot.position, WarehousObject.FREE_SPACE);
                    robot.position = possibleNextRobotPosition;
                    warehouseObjects.remove(possibleNextRobotPosition);
                } else if (warehousObject != null && warehousObject.equals(WarehousObject.BOX)) {
                    Vector2D nextPosition = possibleNextRobotPosition.add(movementVector);
                    WarehousObject nextWarehouseObject = warehouseObjects.get(nextPosition);
                    List<Vector2D> newBoxPositions = new ArrayList<>();
                    boolean canMove = false;
                    while (nextWarehouseObject != null && (nextWarehouseObject.equals(WarehousObject.BOX) || nextWarehouseObject.equals(WarehousObject.FREE_SPACE))) {
                        newBoxPositions.add(nextPosition);
                        nextPosition = nextPosition.add(movementVector);
                        if (nextWarehouseObject.equals(WarehousObject.FREE_SPACE)) {
                            canMove = true;
                            break;
                        }
                        nextWarehouseObject = warehouseObjects.get(nextPosition);
                    }
                    if (canMove) {
                        for (Vector2D newBoxPosition : newBoxPositions) {
                            warehouseObjects.put(newBoxPosition, WarehousObject.BOX);
                        }
                        warehouseObjects.put(robot.position, WarehousObject.FREE_SPACE);
                        robot.position = possibleNextRobotPosition;
                        warehouseObjects.remove(possibleNextRobotPosition);
                    }
                }
                //draw();
            }
        }

        private void moveRobot2() {
            System.out.println("Initial state:");
            draw2();
            while (!movements.isEmpty()) {
                Character nextMovement = movements.poll();
                System.out.println("Move " + nextMovement + ":");
                Vector2D movementVector = getNextRobotPosition(nextMovement);
                Vector2D possibleNextRobotPosition = robot.position.add(movementVector);
                WarehousObject warehousObject = warehouseObjects.get(possibleNextRobotPosition);
                if (warehousObject != null && warehousObject.equals(WarehousObject.FREE_SPACE)) {
                    warehouseObjects.put(robot.position, WarehousObject.FREE_SPACE);
                    robot.position = possibleNextRobotPosition;
                    warehouseObjects.remove(possibleNextRobotPosition);
                } else if (warehousObject != null && warehousObject.equals(WarehousObject.BOX)) {
                    Vector2D nextPosition = possibleNextRobotPosition.add(movementVector);
                    WarehousObject nextWarehouseObject = warehouseObjects.get(nextPosition);
                    List<Vector2D> newBoxPositions = new ArrayList<>();
                    boolean canMove = false;
                    while (nextWarehouseObject != null && (nextWarehouseObject.equals(WarehousObject.BOX) || nextWarehouseObject.equals(WarehousObject.FREE_SPACE))) {
                        newBoxPositions.add(nextPosition);
                        nextPosition = nextPosition.add(movementVector);
                        if (nextWarehouseObject.equals(WarehousObject.FREE_SPACE)) {
                            canMove = true;
                            break;
                        }
                        nextWarehouseObject = warehouseObjects.get(nextPosition);
                    }
                    if (canMove) {
                        for (Vector2D newBoxPosition : newBoxPositions) {
                            warehouseObjects.put(newBoxPosition, WarehousObject.BOX);
                        }
                        warehouseObjects.put(robot.position, WarehousObject.FREE_SPACE);
                        robot.position = possibleNextRobotPosition;
                        warehouseObjects.remove(possibleNextRobotPosition);
                    }
                }
                draw2();
            }
        }

        private Vector2D getNextRobotPosition(Character move) {
            return move == '^' ? new Vector2D(0, -1) : move == '<' ? new Vector2D(-1, 0) :
                    move == '>' ? new Vector2D(1, 0) : new Vector2D(0, 1);
        }

        private void draw() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Vector2D currentVector = new Vector2D(x, y);
                    WarehousObject currentWareHouseObject = warehouseObjects.get(currentVector);
                    if(currentWareHouseObject != null) {
                        System.out.print(currentWareHouseObject.equals(WarehousObject.WALL) ? '#' : currentWareHouseObject.equals(WarehousObject.BOX) ? "O" : ".");
                    } else if(robot.position.equals(currentVector)) {
                        System.out.print(RED + "@" + RESET);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

        private void draw2() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width * 2; x++) {
                    Vector2D currentVector = new Vector2D(x, y);
                    WarehousObject currentWareHouseObject = warehouseObjects.get(currentVector);
                    if(currentWareHouseObject != null) {
                        System.out.print(currentWareHouseObject.equals(WarehousObject.WALL) ? '#' : currentWareHouseObject.equals(WarehousObject.BOX) ? "O" : ".");
                    } else if(robot.position.equals(currentVector)) {
                        System.out.print(RED + "@" + RESET);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static class Robot {
        private Vector2D position;
    }

    private static class Box {
        Vector2D leftVector;
        Vector2D rightVector;
    }

    private enum WarehousObject {
        WALL, BOX, FREE_SPACE
    }
}