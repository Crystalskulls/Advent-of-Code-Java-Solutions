package com.github.crystalskulls.aoc._2024.day9;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day9 extends Puzzle {

    private String diskMap;

    public Day9() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day9/input.txt";
    }

    @Override
    protected void solvePart1() {
        List<String> disk = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < diskMap.length(); i++) {
            int n = Integer.parseInt("" + diskMap.charAt(i));
            if(i % 2 == 0) {
                for (int j = 0; j < n; j++) {
                    disk.add(""+k);
                }
                k++;
            } else {
                for (int h = 0; h < n; h++) {
                    disk.add(".");
                }
            }
            //System.out.println(disk);
        }
        for (int i = 0; i < disk.size(); i++) {
            if(disk.get(i).equals(".")) {
                for (int j = disk.size() - 1; j > i ; j--) {
                    String number = disk.get(j);
                    if(number.equals(".")) {
                        continue;
                    }
                    disk.remove(i);
                    disk.add(i, number);
                    disk.remove(j);
                    disk.add(j, ".");
                    break;
                }
            }
            //System.out.println(disk);
        }

        Long sum = 0L;
        for (int v = 0; v < disk.size(); v++) {
            if(disk.get(v).equals(".")) {
                break;
            }
            long tmp = v * Long.parseLong(disk.get(v));
            sum += tmp;
        }
        System.out.println("Part 1: " + sum);
    }

    @Override
    protected void solvePart2() {
        List<String> disk = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < diskMap.length(); i++) {
            int n = Integer.parseInt("" + diskMap.charAt(i));
            if(i % 2 == 0) {
                for (int j = 0; j < n; j++) {
                    disk.add(""+k);
                }
                k++;
            } else {
                for (int h = 0; h < n; h++) {
                    disk.add(".");
                }
            }
            //System.out.println(disk);
        }
        System.out.println("disk size: " + disk.size());

        for (int i = disk.size() - 1; i >= 0; i--) {
            //System.out.println(disk);
            String id = disk.get(i);
            if(disk.get(i).equals(".")) {
                continue;
            }
            int endIndex = i-1;
            int length = 1;
            while(endIndex >= 0 && disk.get(endIndex).equals(id)) {
                length++;
                endIndex--;
            }
            //System.out.println("length: " + length);

            for (int j = 0; j <= endIndex; j++) {
                String currrent = disk.get(j);
                int freeSpace = 1;
                if(currrent.equals(".")) {
                    //System.out.println("j: " + j);
                    int z = j+1;
                    while(z <= endIndex && disk.get(z).equals(".")) {
                        freeSpace++;
                        z++;
                    }
                    //System.out.println("freeSpace: " + freeSpace);
                    if(freeSpace >= length) {
                        for (int m = j; m <= j+(length-1); m++) {
                            //System.out.println("m: " + m);
                            disk.remove(m);
                            disk.add(m, id);
                        }
                        for (int w = endIndex +1; w <= i; w++) {
                            disk.remove(w);
                            disk.add(w, ".");
                        }
                        break;
                    }
                }
            }
            //System.out.println(disk);
            i -= length-1;
        }

        Long sum = 0L;
        for (int v = 0; v < disk.size(); v++) {
            if(disk.get(v).equals(".")) {
                continue;
            }
            long tmp = v * Long.parseLong(disk.get(v));
            sum += tmp;
        }
        System.out.println("Part 2: " + sum);
    }

    @Override
    protected void readInputData(String inputFile) {
        diskMap = FileReader.read(inputFile);
    }
}