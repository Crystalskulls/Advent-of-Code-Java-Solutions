package com.github.crystalskulls.aoc._2024.day23;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class Day23 extends Puzzle {

    Map<String, Computer> hostnameToComputerMap = new HashMap<>();
    List<List<Computer>> threeInterConnectedComputerList = new ArrayList<>();

    public Day23() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day23/input.txt";
    }

    @Override
    protected void solvePart1() {
        for (String hostname : hostnameToComputerMap.keySet()) {
            Computer primaryComputer = hostnameToComputerMap.get(hostname);
            Set<Computer> secondaryConnectedComputers = primaryComputer.connectedComputers;
            for (Computer secondaryComputer : secondaryConnectedComputers) {
                Set<Computer> ternaryConnectedComputers = secondaryComputer.connectedComputers;
                for (Computer ternaryComputer : ternaryConnectedComputers) {
                    Set<Computer> thirdConnectedComputers = ternaryComputer.connectedComputers;
                    if(thirdConnectedComputers.contains(primaryComputer)) {
                        List<Computer> threeInterConnectedComputers = new ArrayList<>(List.of(primaryComputer, secondaryComputer, ternaryComputer));
                        threeInterConnectedComputers.sort(Comparator.comparing(c -> c.hostname));
                        if(!threeInterConnectedComputerList.contains(threeInterConnectedComputers) && threeInterConnectedComputers.stream().anyMatch(computer -> computer.hostname.startsWith("t"))) {
                            threeInterConnectedComputerList.add(threeInterConnectedComputers);
                        }
                    }
                }
            }
        }
        System.out.println(threeInterConnectedComputerList.size());
    }

    @Override
    protected void solvePart2() {
        Set<String> hostnames = hostnameToComputerMap.keySet();
        for (String s : hostnameToComputerMap.keySet()) {
            System.out.println(s + " : " + hostnameToComputerMap.get(s).connectedComputers);
        }
        boolean stop = false;
        Set<String> connectedToAllComputers = new HashSet<>();
        Computer currentComputer = hostnameToComputerMap.get("co");
        Set<Computer> currentConnectedComputers = new HashSet<>(currentComputer.connectedComputers);
        List<List<Computer>> lc = new ArrayList<>();
        currentConnectedComputers.add(currentComputer);
        for (Computer connectedComputer : currentComputer.connectedComputers) {
            Set<Computer> tmp = new HashSet<>(connectedComputer.connectedComputers);
            tmp.add(connectedComputer);
            List<Computer> abc = new ArrayList<>(currentConnectedComputers);
            abc.retainAll(tmp);
            lc.add(abc);
        }
        System.out.println(lc);
    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> connectedComputers = FileReader.readAllLines(inputFile);
        connectedComputers.forEach(this::addComputersToNetwork);
    }

    void addComputersToNetwork(String connectedComputers) {
        String[] computers = connectedComputers.split("-");
        Computer computer1 = hostnameToComputerMap.getOrDefault(computers[0], new Computer(computers[0]));
        Computer computer2 = hostnameToComputerMap.getOrDefault(computers[1], new Computer(computers[1]));
        computer1.connectedComputers.add(computer2);
        computer2.connectedComputers.add(computer1);
        hostnameToComputerMap.put(computer1.hostname, computer1);
        hostnameToComputerMap.put(computer2.hostname, computer2);
    }

    @ToString(exclude = { "connectedComputers" })
    @EqualsAndHashCode(exclude = { "connectedComputers" })
    private static class Computer {
        String hostname;
        Set<Computer> connectedComputers = new HashSet<>();

        Computer(String hostname) {
            this.hostname = hostname;
        }
    }
}