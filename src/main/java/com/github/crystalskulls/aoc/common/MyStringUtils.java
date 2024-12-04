package com.github.crystalskulls.aoc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class MyStringUtils {

    public static List<String> extractLinesColumnsAndDiagonals(String inputFile) {
        List<String> lines = extractLines(inputFile);
        List<String> columns = extractColumns(lines);
        List<String> diagonals = extractDiagonals(lines);
        return Stream.concat(Stream.concat(lines.stream(), columns.stream()), diagonals.stream()).toList();
    }

    private static List<String> extractLines(String inputFile) {
        return FileReader.readAllLines(inputFile);
    }

    private static List<String> extractColumns(List<String> lines) {
        String[] columnsArray = new String[lines.size()];
        List<String> columns = new ArrayList<>();
        Arrays.fill(columnsArray, "");
        for (String line : lines) {
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                columnsArray[i] += chars[i];
            }
        }
        Collections.addAll(columns, columnsArray);
        return columns;
    }

    private static List<String> extractDiagonals(List<String> lines) {
        String firstLine = lines.getFirst();
        List<String> diagonals = new ArrayList<>();
        char[] firstLineChars = firstLine.toCharArray();
        StringBuilder diagonal = new StringBuilder();
        for (int j = 0; j < firstLineChars.length; j++) {
            diagonal.append(firstLineChars[j]);
            for (int k = 1; k < lines.size(); k++) {
                if (j - k == -1) {
                    break;
                }
                diagonal.append(lines.get(k).toCharArray()[j - k]);
            }
            diagonals.add(diagonal.toString());
            diagonal = new StringBuilder();
        }

        for (int j = 1; j < lines.size(); j++) {
            int k = 1;
            for (int i = j; i < lines.size(); i++) {
                char[] tmp = lines.get(i).toCharArray();
                diagonal.append(tmp[tmp.length - k]);
                k++;
            }
            diagonals.add(diagonal.toString());
            diagonal = new StringBuilder();
        }

        for (int j = 0; j < firstLineChars.length; j++) {
            diagonal.append(firstLineChars[j]);
            for (int k = 1; k < lines.size(); k++) {
                if (j + k == firstLineChars.length) {
                    break;
                }
                diagonal.append(lines.get(k).toCharArray()[j + k]);
            }
            diagonals.add(diagonal.toString());
            diagonal = new StringBuilder();
        }

        for (int j = 1; j < lines.size(); j++) {
            for (int i = j; i < lines.size(); i++) {
                char[] tmp = lines.get(i).toCharArray();
                diagonal.append(tmp[i-j]);
            }
            diagonals.add(diagonal.toString());
            diagonal = new StringBuilder();
        }
        return diagonals;
    }
}
