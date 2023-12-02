package com.github.crystalskulls.aoc.datastructures.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileSystemNode {

    private String name;
    private int size;
    private boolean isDirectory;
    private List<FileSystemNode> children = new ArrayList<>();
    private FileSystemNode parent;
}
