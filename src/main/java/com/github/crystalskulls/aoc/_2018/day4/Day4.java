package com.github.crystalskulls.aoc._2018.day4;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 extends Puzzle {

    List<Record> records = new ArrayList<>();
    Map<String, Guard> guardMap = new HashMap<>();

    public Day4() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2018/day4/input.txt";
    }

    @Override
    protected void solvePart1() {
        records = records.stream().sorted(Comparator.comparing(o -> o.timestamp)).toList();
        String id = "";
        Timestamp wakesUp = null;
        Timestamp fallsAsleep = null;
        for (Record record : records) {
            if(record.note.startsWith("Guard")) {
                id = record.note.split(" #")[1].split(" ")[0];
                guardMap.computeIfAbsent(id, Guard::new);
            } else if(record.note.startsWith("wakes up")) {
                wakesUp = record.timestamp;
                if(fallsAsleep != null) {
                    for (int minuteAsleep = fallsAsleep.getMinutes(); minuteAsleep < wakesUp.getMinutes(); minuteAsleep++) {
                        guardMap.get(id).minutesAsleepMap.compute(minuteAsleep, (k, v) -> v == null ? 1 : v + 1);
                    }
                }
            } else {
                fallsAsleep = record.timestamp;
            }
        }
        int max = 0;
        Guard choosenGuard = null;
        for (String k : guardMap.keySet()) {
            int minutesAsleep = guardMap.get(k).minutesAsleepMap.values().stream().mapToInt(Integer::intValue).sum();
            if(minutesAsleep > max) {
                choosenGuard = guardMap.get(k);
                max = minutesAsleep;
            }
        }
        int mm = 0;
        int r = 0;
        for (Integer m : choosenGuard.minutesAsleepMap.keySet()) {
            int mas = choosenGuard.minutesAsleepMap.get(m);
            if(mas > mm) {
                r = m;
                mm = mas;
            }
        }
        System.out.println(choosenGuard.id);
        System.out.println(r);
        System.out.println(Integer.parseInt(choosenGuard.id) * r);

        int maxi = 0;
        int r2 = 0;
        for (String k : guardMap.keySet()) {

            List<Integer> abc = guardMap.get(k).minutesAsleepMap.values().stream().sorted().toList();
            if(abc.size() > 0) {
                int a = abc.getLast();
                if(a > maxi) {
                    choosenGuard = guardMap.get(k);
                    maxi = a;
                }
            }
        }
        System.out.println("---");
        System.out.println(maxi);
        System.out.println(choosenGuard.id);
        System.out.println(choosenGuard.minutesAsleepMap);
        for (Integer i : choosenGuard.minutesAsleepMap.keySet()) {
            if (choosenGuard.minutesAsleepMap.get(i) == maxi) {
                System.out.println("fertig");
                System.out.println(Integer.parseInt(choosenGuard.id) * i);
                break;
            }
        }
    }

    @Override
    protected void solvePart2() {

    }

    @Override
    protected void readInputData(String inputFile) {
        FileReader.readAllLines(inputFile).forEach(line -> {
            String[] recordSnippets = line.split("] ");

            Timestamp timestamp = Timestamp.valueOf(recordSnippets[0].substring(1) + ":00");
            String note = recordSnippets[1];
            records.add(new Record(timestamp, note));
        });
    }

    private record Record(Timestamp timestamp, String note) {}

    private class Guard {
        private String id;
        private Map<Integer, Integer> minutesAsleepMap = new HashMap<>();

        Guard(String id) {
            this.id = id;
        }
    }
}
