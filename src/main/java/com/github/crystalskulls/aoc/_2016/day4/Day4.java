package com.github.crystalskulls.aoc._2016.day4;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.*;

public class Day4 extends Puzzle {

    List<Room> rooms = new ArrayList<>();

    public Day4() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2016/day4/input.txt";
    }

    @Override
    protected void solvePart1() {
        System.out.println("Part 1: " + rooms.stream().mapToInt(room -> room.isRealRoom() ? room.sectorId : 0).sum());
    }

    @Override
    protected void solvePart2() {
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> roomInstructions = FileReader.readAllLines(inputFile);
        roomInstructions.forEach(roomInstruction -> rooms.add(parseRoom(roomInstruction)));
    }

    private Room parseRoom(String roomInstruction) {
        String[] snippets = roomInstruction.split("-");
        String sectorIdAndChecksum = snippets[snippets.length -1];
        String[] sectorIdAndChecksumSnippets = sectorIdAndChecksum.split("\\[");
        int sectorId = Integer.parseInt(sectorIdAndChecksumSnippets[0]);
        char[] checksum = sectorIdAndChecksumSnippets[1].replace("]", "").toCharArray();
        List<String> encryptedNameList = new ArrayList<>(Arrays.stream(snippets).toList());
        encryptedNameList.removeLast();
        char[] encryptedName = String.join("", encryptedNameList).toCharArray();
        return new Room(encryptedName, sectorId, checksum);
    }

    @AllArgsConstructor
    @ToString
    private class Room {
        private char[] encryptedName;
        private int sectorId;
        private char[] checksum;

        boolean isRealRoom() {
            Map<Character, Integer> lettersCountMap = countLettersInEncryptedName();
            return isChecksumValid(lettersCountMap);
        }

        private boolean isChecksumValid(Map<Character, Integer> lettersCountMap) {
            int lastLetterCount = lettersCountMap.values().stream().max(Integer::compare).get();
            lettersCountMap.values().stream().max(Integer::compare).get();
            char lastLetter = Collections.max(lettersCountMap.entrySet(), Map.Entry.comparingByValue()).getKey();
            for (char c : checksum) {
                int currentLetterCount = lettersCountMap.getOrDefault(c, 0);
                if (currentLetterCount > lastLetterCount) {
                    return false;
                }
                if (currentLetterCount == lastLetterCount) {
                    if (c < lastLetter) {
                        return false;
                    }
                }
                lastLetterCount = currentLetterCount;
                lastLetter = c;
            }
            return true;
        }

        private Map<Character, Integer> countLettersInEncryptedName() {
            Map<Character, Integer> lettersCountMap = new HashMap<>();
            for (char c : encryptedName) {
                lettersCountMap.merge(c, 1, Integer::sum);
            }
            return lettersCountMap;
        }
    }
}
