package com.github.crystalskulls.aoc._2022.day7;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import com.github.crystalskulls.aoc.datastructures.tree.FileSystemNode;

import java.util.List;
import java.util.Objects;

public class Day7 extends Puzzle {

    private List<String> filesystemCommands;
    private FileSystemNode root;
    public Day7() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2022/day7/input.txt";
    }

    @Override
    protected void solvePart1() {
        this.createFileSystem();
    }

    private void createFileSystem() {
        this.root = new FileSystemNode();
        root.setName("/");
        root.setDirectory(true);
        FileSystemNode currentDirectory = root;
        this.filesystemCommands.forEach(command -> {
            String[] commandTokens = command.split(" ");
            String firstToken = commandTokens[0];
            if(Objects.equals(firstToken, "$") && Objects.equals(command, Command.LS.name)) {
                // ls
                System.out.println(command);
            } else if(Objects.equals(firstToken, "$") && Objects.equals(command, Command.CD.name)) {
                // cd
                if(Objects.equals(commandTokens[2], "..")) {
                    // cd ..
                } else {

                    // cd a
                }
            } else if(Objects.equals(firstToken, "dir")) {
                // dir
                String directoryName = commandTokens[1];
                FileSystemNode newDirectory = new FileSystemNode();
                newDirectory.setName(directoryName);
                newDirectory.setParent(currentDirectory);
                newDirectory.setDirectory(true);
                newDirectory.getChildren().add(newDirectory);
            } else {
                // file
                int fileSize = Integer.parseInt(commandTokens[0]);
                String fileName = commandTokens[1];
                FileSystemNode newFile = new FileSystemNode();
                newFile.setName(fileName);
                newFile.setSize(fileSize);
                newFile.setParent(currentDirectory);
            }
        });
    }

    @Override
    protected void solvePart2() {

    }

    private enum Command {

        LS("ls"),
        CD("cd");


        private final String name;

        private Command(String name) {
            this.name = name;
        }
    }

    @Override
    protected void readInputData(String inputFile) {
        this.filesystemCommands = FileReader.readAllLines(inputFile);
        this.filesystemCommands.remove(0);
    }
}
