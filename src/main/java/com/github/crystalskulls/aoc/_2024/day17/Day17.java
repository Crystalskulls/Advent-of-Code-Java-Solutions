package com.github.crystalskulls.aoc._2024.day17;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 extends Puzzle {

    private Computer computer;

    public Day17() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day17/input.txt";
    }

    @Override
    protected void solvePart1() {
        computer.runProgram();
        System.out.println("Part 1: " + StringUtils.join(computer.output, ","));
    }

    @Override
    @SneakyThrows
    protected void solvePart2() {
        readInputData(this.inputFile);
        List<Runnable> tasks = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1000);
        for (long i = 0; i < 100_000_000_000L; i += 100_000_000L) {
            tasks.add(calculateRegisterA(i, latch));
        }
        for (Runnable task : tasks) {
            Thread.startVirtualThread(task);
        }
        latch.await();
        System.out.println("Fertig");
    }

    private Runnable calculateRegisterA(long startIndex, CountDownLatch latch) {
        return () -> {
            try {
                for (long i = startIndex; i < startIndex + 100_000_000L; i++) {
                    Computer tmpComputer = new Computer();
                    tmpComputer.registerA = i;
                    tmpComputer.registerB = computer.registerB;
                    tmpComputer.registerC = computer.registerC;
                    tmpComputer.program = computer.program;
                    tmpComputer.runProgram();
                    if(computer.program.equals(tmpComputer.output)) {
                        System.out.println("Part 2: " + i);
                        System.exit(0);
                    }
                }
            } finally {
                latch.countDown();
            }
        };
    }

    @Override
    protected void readInputData(String inputFile) {
        computer = new Computer();
        Pattern numberPattern = Pattern.compile("\\d+");
        String input = FileReader.read(inputFile);
        Matcher numberMatcher = numberPattern.matcher(input);
        numberMatcher.find();
        computer.registerA = Long.parseLong(numberMatcher.group());
        numberMatcher.find();
        computer.registerB = Long.parseLong(numberMatcher.group());
        numberMatcher.find();
        computer.registerC = Long.parseLong(numberMatcher.group());
        while(numberMatcher.find()) {
            computer.program.add(Long.parseLong(numberMatcher.group()));
        }
    }

    @ToString
    private static class Computer {
        List<Long> program = new ArrayList<>();
        List<Long> output = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        int instructionPointer;
        long registerA;
        long registerB;
        long registerC;

        private void runProgram() {
            parseInstructions();
            for (instructionPointer = 0; instructionPointer < instructions.size();) {
                Instruction instruction = instructions.get(instructionPointer);
                //System.out.println("Run instruction: " + instruction);
                switch (instruction.opcode) {
                    case adv -> adv(instruction.operand);
                    case bxl -> bxl(instruction.operand);
                    case bst -> bst(instruction.operand);
                    case jnz -> jnz(instruction.operand);
                    case bxc -> bxc(instruction.operand);
                    case out -> output.add(out(instruction.operand));
                    case bdv -> bdv(instruction.operand);
                    case cdv -> cdv(instruction.operand);
                }
                if(instruction.increaseInstructionPointer) {
                    instructionPointer++;
                }
                instruction.increaseInstructionPointer = true;
            }
        }

        private void adv(int operand) {
            long numerator = registerA;
            int denominator = (int)Math.pow(2, getComboOperand(operand));
            registerA =  numerator / denominator;
        }

        private void bxl(int operand) {
            registerB = registerB ^ operand;
        }

        private void bst(int operand) {
            registerB = getComboOperand(operand) % 8;
        }

        private void jnz(int operand) {
            if(registerA == 0) {
                return;
            }
            instructions.get(instructionPointer).increaseInstructionPointer = false;
            instructionPointer = operand % 2;
            Instruction nextInstruction = instructions.get(instructionPointer);
            if(Opcode.jnz.equals(nextInstruction.opcode)) {
                nextInstruction.increaseInstructionPointer = false;
            }
        }

        private void bxc(int operand) {
            registerB = registerB ^ registerC;
        }

        private long out(int operand) {
            return getComboOperand(operand) % 8;
        }

        private void bdv(int operand) {
            long numerator = registerA;
            long denominator = (long)Math.pow(2, getComboOperand(operand));
            registerB =  numerator / denominator;
        }

        private void cdv(int operand) {
            long numerator = registerA;
            long denominator = (long)Math.pow(2, getComboOperand(operand));
            registerC =  numerator / denominator;
        }

        private long getComboOperand(int operand) {
            if(operand >= 0 && operand <= 3) {
                return operand;
            }
            if(operand == 4) {
                return registerA;
            }
            if(operand == 5) {
                return registerB;
            }
            if(operand == 6) {
                return registerC;
            }
            throw new RuntimeException("Combo operand 7 is reserved and will not appear in valid programs.");
        }

        private void parseInstructions() {
            for (int instructionPointer = 0; instructionPointer < program.size() - 1; instructionPointer += 2) {
                instructions.add(new Instruction(Opcode.fromId(Math.toIntExact(program.get(instructionPointer))), Math.toIntExact(program.get(instructionPointer + 1)), true));
            }
            //System.out.println(instructions);
        }
    }

    @ToString
    private enum Opcode {
        adv(0), bxl(1), bst(2), jnz(3), bxc(4), out(5), bdv(6), cdv(7);

        int id;

        Opcode(int id) {
            this.id = id;
        }

        public static Opcode fromId(int id) {
            for (Opcode opcode : Opcode.values()) {
                if (opcode.id == id) {
                    return opcode;
                }
            }
            return null;
        }
    }

    @AllArgsConstructor
    @ToString
    private static class Instruction{
        Opcode opcode;
        int operand;
        boolean increaseInstructionPointer;
    }
}