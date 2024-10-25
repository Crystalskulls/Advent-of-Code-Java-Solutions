package com.github.crystalskulls.aoc._2015.day4;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.codec.digest.DigestUtils;

public class Day4 extends Puzzle {

    private String secretKey;

    public Day4() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2015/day4/input.txt";
    }

    @Override
    protected void solvePart1() {
        int i = 0;
        while(true) {
            String hash = DigestUtils.md5Hex(secretKey + i);
            if(hash.startsWith("00000")) {
                System.out.println(hash);
                break;
            }
            i++;
        }
        System.out.println("Part 1: "  + i);
    }

    @Override
    protected void solvePart2() {
        int i = 0;
        while(true) {
            String hash = DigestUtils.md5Hex(secretKey + i);
            if(hash.startsWith("000000")) {
                System.out.println(hash);
                break;
            }
            i++;
        }
        System.out.println("Part 2: " + i);
    }

    @Override
    protected void readInputData(String inputFile) {
        secretKey = FileReader.read(inputFile);
    }
}