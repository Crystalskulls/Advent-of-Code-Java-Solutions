package com.github.crystalskulls.aoc._2023.day13;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Pattern {

    private List<String> rows;
    private List<String> columns;

    public Pattern(String patternString) {
        this.createRows(patternString);
        this.createColumns();
    }

    private void createRows(String patternString) {
        this.rows = Arrays.stream(patternString.split("\n")).toList();
    }

    private void createColumns() {
        Map<Integer, String> columnsMap = new HashMap<>();
        for (String row : this.rows) {
            char[] characters = row.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                String s = columnsMap.get(i) == null ? "" : columnsMap.get(i);
                columnsMap.put(i, s + characters[i]);
            }
        }
        this.columns = columnsMap.values().stream().toList();
    }

    public int findVerticalReflection() {
        return this.findReflection(this.columns);
    }

    public int findHorizontalReflection() {
        return this.findReflection(this.rows);
    }

    private int findReflection(List<String> strings) {
        for (int i = 0; i < strings.size() - 1; i++) {
            if(strings.get(i).equals(strings.get(i + 1))) {
                boolean hasPerfectReflection = true;
                for (int j = i - 1; j >= 0; j--) {
                    int comparingIndex = i + (i - j) + 1;
                    if(comparingIndex == strings.size()) {
                        break;
                    }
                    if(!strings.get(j).equals(strings.get(comparingIndex))) {
                        hasPerfectReflection = false;
                    }
                }
                if(hasPerfectReflection) {
                    return i + 1;
                }
            }
        }
        return -1;
    }
}
