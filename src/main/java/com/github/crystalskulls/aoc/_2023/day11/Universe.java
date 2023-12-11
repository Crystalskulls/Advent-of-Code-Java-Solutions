package com.github.crystalskulls.aoc._2023.day11;

import com.github.crystalskulls.aoc.common.Regex;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Stream;

@Getter
@Setter
public class Universe {

    private List<List<Character>> image = new ArrayList<>();
    private Node[][] nodeSystem;
    private List<Galaxy> galaxies = new ArrayList<>();
    private Map<String, Node> nodeMap = new HashMap<>();
    private long expandedBy;

    public Universe(long expandedBy) {
        this.expandedBy = expandedBy;
    }

    public void expand() {
        this.expandRows();
        this.expandColumns();
    }

    private void printNodeSystemWithExpanded() {
        for (Node[] nodes : this.nodeSystem) {
            for (Node node : nodes) {
                if(node instanceof Galaxy) {
                    System.out.print("#");
                } else {
                    if(node.isExpanded()) {
                        System.out.print(";");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }
    }

    public void createNodeMap() {
        for (int y = 0; y < this.image.size(); y++) {
            for (int x = 0; x < this.image.get(y).size(); x++) {
                String coordinate = String.format("(%s,%s)", x, y);
                char character = this.image.get(y).get(x);
                if(character == '#') {
                    Galaxy galaxy = new Galaxy(x, y);
                    this.nodeMap.put(coordinate, galaxy);
                    this.galaxies.add(galaxy);
                } else {
                    this.nodeMap.put(coordinate, new Space(x, y));
                }
            }
        }
    }

    public void createGraph() {
        this.nodeMap.forEach((coordinate, node) -> {
            node.addAdjacentNodes(this.nodeMap);
        });
    }

    public void createNodeSystem() {
        this.nodeSystem = new Node[this.image.size()][this.image.getFirst().size()];
        this.nodeMap.forEach((coordinate, node) -> {
            List<Integer> coordinates = Regex.findNumbers(coordinate, Regex.positiveNumberPattern, Integer::parseInt);
            int x = coordinates.getFirst(), y = coordinates.getLast();
            this.nodeSystem[y][x] = node;
        });
    }

    private void printImage() {
        for (List<Character> characters : this.image) {
            for (Character character : characters) {
                System.out.print(character);
            }
            System.out.println();
        }
    }

    private void printNodeSystem() {
        for (Node[] nodes : this.nodeSystem) {
            for (Node node : nodes) {
                if(node instanceof Galaxy) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private void printNodeSystemWithDistances() {
        for (Node[] nodes : this.nodeSystem) {
            for (Node node : nodes) {
                System.out.print(node.getDistance() + "  ");
            }
            System.out.println();
        }
    }

    private void expandRows() {
        List<Node> expandedNodes = new ArrayList<>();
        for (int i = 0; i < this.nodeSystem.length; i++) {
            if(this.containsNoGalaxy(Arrays.stream(this.nodeSystem[i]))) {
                Collections.addAll(expandedNodes, this.nodeSystem[i]);
            }
        }
        for (Node node : expandedNodes) {
            node.setExpanded(true);
        }
    }

    private void expandColumns() {
        for (int i = 0; i < this.nodeSystem[0].length; i++) {
            List<Node> nodeList = new ArrayList<>();
            for (Node[] nodes : this.nodeSystem) {
                nodeList.add(nodes[i]);
            }
            if(this.containsNoGalaxy(nodeList.stream())) {
                for (Node node : nodeList) {
                    node.setExpanded(true);
                }
            }
        }
    }

    private boolean containsNoGalaxy(Stream<Node> nodes) {
        return nodes.allMatch(node -> node instanceof Space);
    }

    private void resetDistances() {
        this.nodeMap.forEach((coordinate, node) -> {
            node.setDistance(null);
        });
    }

    public Long exploreGalaxies() {
        long sum = 0;
        for (int i = 0; i < this.galaxies.size(); i++) {
            Galaxy startGalaxy = this.galaxies.get(i);
            startGalaxy.setDistance(0L);
            List<Node> queue = new ArrayList<>();
            queue.add(startGalaxy);
            this.dijkstra(queue);
            //this.printNodeSystemWithDistances();
            //System.exit(1);
            for (int j = i+1; j < this.galaxies.size(); j++) {
                sum += this.galaxies.get(j).getDistance();
            }
            //this.printNodeSystemWithDistances();
            this.resetDistances();
        }
        return sum;
    }

    private void dijkstra(List<Node> queue) {
        while(!queue.isEmpty()) {
            Node node = queue.remove(0);
            List<Node> adjacentNodes = node.getAdjacentNodes();
            for (Node adjacentNode : adjacentNodes) {
                if(adjacentNode.getDistance() == null) {
                    queue.add(adjacentNode);
                    long expandedBy = adjacentNode.isExpanded() ? this.expandedBy : 0L;
                    adjacentNode.setDistance(node.getDistance() + 1L + expandedBy);
                }
            }
            queue.sort(Comparator.comparingLong(Node::getDistance));
        }
    }
}