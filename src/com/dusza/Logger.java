package com.dusza;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private Path logPath;
    private int taskNumber = 1;
    private final List<String> logList = new ArrayList<>();

    public Logger(Path logPath) {
        this.logPath = logPath;

        logList.add("1. feladat");
        System.out.println("1. feladat");
    }

    public void nextTask() {
        taskNumber++;
        logList.add("");
        logList.add(taskNumber + ". feladat");
        System.out.println("\n" + taskNumber + ". feladat");
    }

    public void log(Object s) {
        logList.add(s.toString());
        System.out.println(s);
    }

    public void save() {
        try(BufferedWriter writer = Files.newBufferedWriter(logPath)) {
            for(String s : logList) {
                writer.write(s + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
