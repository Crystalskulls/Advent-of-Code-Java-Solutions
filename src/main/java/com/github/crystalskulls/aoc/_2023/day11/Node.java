package com.github.crystalskulls.aoc._2023.day11;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Node {

    protected int x;
    protected int y;
    protected List<Node> adjacentNodes = new ArrayList<>();
    protected Long distance;
    protected boolean expanded;

    private final String topLeftNode;
    private final String topNode;
    private final String topRightNode;
    private final String leftNode;
    private final String rightNode;
    private final String downLeftNode;
    private final String downNode;
    private final String downRightNode;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        String coordinateFormat = "(%s,%s)";
        this.topLeftNode = String.format(coordinateFormat, this.x-1, this.y-1);
        this.topNode = String.format(coordinateFormat, this.x, this.y-1);
        this.topRightNode = String.format(coordinateFormat, this.x+1, this.y-1);
        this.leftNode = String.format(coordinateFormat, this.x-1, this.y);
        this.rightNode = String.format(coordinateFormat, this.x+1, this.y);
        this.downLeftNode = String.format(coordinateFormat, this.x-1, this.y+1);
        this.downNode = String.format(coordinateFormat, this.x, this.y+1);
        this.downRightNode = String.format(coordinateFormat, this.x+1, this.y+1);
    }

    protected void addAdjacentNodes(Map<String, Node> image) {
        //this.adjacentNodes.add(image.get(this.topLeftNode));
        this.addAdjacentNode(image.get(this.topNode));
        //this.adjacentNodes.add(image.get(this.topRightNode));
        this.addAdjacentNode(image.get(this.leftNode));
        this.addAdjacentNode(image.get(this.rightNode));
        //this.adjacentNodes.add(image.get(this.downLeftNode));
        this.addAdjacentNode(image.get(this.downNode));
        //this.adjacentNodes.add(image.get(this.downRightNode));
    }

    private void addAdjacentNode(Node node) {
        if(node == null) {
            return;
        }
        this.adjacentNodes.add(node);
    }
}
