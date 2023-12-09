package com.github.crystalskulls.aoc._2023.day8;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Math;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 extends Puzzle {

    private final Pattern threeCharacterPattern = Pattern.compile("[A-Z1-9][A-Z1-9][A-Z1-9]");
    private List<Integer> navigationInstruction;
    private final Map<String, String[]> nodeMap = new HashMap<>();
    private List<String> startingNodes;

    public Day8() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day8/input.txt";
    }

    @Override
    protected void solvePart1() {
        int steps = explore("AAA", node -> !Objects.equals(node, "ZZZ"));
        System.out.println("Part 1: " + steps);
    }

    @Override
    protected void solvePart2() {
        List<Integer> steps = new ArrayList<>();
        for (String startingNode : this.startingNodes) {
            steps.add(explore(startingNode, node -> !node.endsWith("Z")));
        }
        System.out.println("Part 2: " + Math.lcm(steps.stream().map(BigInteger::valueOf).toArray(BigInteger[]::new)));
    }

    private int explore(String startNode, Predicate<String> predicate) {
        String currentNode = startNode;
        int j = 0, steps = 0;
        while(predicate.test(currentNode)) {
            steps++;
            if(j == this.navigationInstruction.size()) {
                j = 0;
            }
            int i = this.navigationInstruction.get(j);
            String[] leftRightInstruction = this.nodeMap.get(currentNode);
            currentNode = leftRightInstruction[i];
            j++;
        }
        return steps;
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> navigationDocument = FileReader.readAllLines(inputFile);
        this.navigationInstruction = navigationDocument.getFirst().chars().mapToObj(c -> c == 76 ? 0 : 1).toList();

        for (int i = 2; i < navigationDocument.size(); i++) {
            createNodeMap(navigationDocument, i);
        }
        this.startingNodes = this.nodeMap.keySet().stream().filter(node -> node.endsWith("A")).toList();
    }

    private void createNodeMap(List<String> navigationDocument, int i) {
        Matcher matcher = threeCharacterPattern.matcher(navigationDocument.get(i));
        matcher.find();
        String node = matcher.group();
        matcher.find();
        String left = matcher.group();
        matcher.find();
        String right = matcher.group();
        this.nodeMap.put(node, new String[]{left, right});
    }
}
