package com.github.crystalskulls.aoc.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileReader {

    public static List<String> readAllLines(String file) {
        try {
            return Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readAllLines(String file, String regex) {
        try {
            String content = Files.readString(Paths.get(file), StandardCharsets.UTF_8);
            return Arrays.stream(content.split(regex)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static char[] readAllChars(String file) {
        try {
            String content = Files.readString(Paths.get(file), StandardCharsets.UTF_8);
            return content.toCharArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(String file) {
        try {
            return Files.readString(Paths.get(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
