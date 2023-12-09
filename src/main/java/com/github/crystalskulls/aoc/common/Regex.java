package com.github.crystalskulls.aoc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static final Pattern positiveNumberPattern = java.util.regex.Pattern.compile("\\d+");
    public static final Pattern positiveAndNegativeNumberPattern = Pattern.compile("-?\\d+");

    public static <T extends Number> List<T> findNumbers(String numberString, Pattern pattern, Function<String, T> numberConverter) {
        Matcher matcher = pattern.matcher(numberString);
        List<T> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add((numberConverter.apply(matcher.group())));
        }
        return numbers;
    }
}
