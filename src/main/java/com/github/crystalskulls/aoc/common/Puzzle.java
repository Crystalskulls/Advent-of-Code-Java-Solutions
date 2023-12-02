package com.github.crystalskulls.aoc.common;

public abstract class Puzzle {

    protected String inputFile;

    public Puzzle() {

    }

    protected abstract void solvePart1();
    protected abstract void solvePart2();

    public void solve() {
        this.readInputData(this.inputFile);
        this.solvePart1();
        this.solvePart2();
    }

    protected abstract void readInputData(String inputFile);

}
