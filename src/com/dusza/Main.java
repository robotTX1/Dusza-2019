package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path workDir = FileSystems.getDefault().getPath("Data");

        CommandLineInterface cli = new CommandLineInterface(workDir);

        Path filePath = cli.start();

        if(filePath == null) System.exit(0);

        IOHandler ioHandler = new IOHandler(filePath);

        String name = String.valueOf(filePath.getFileName()).split("\\.")[0];
        Path logFile = FileSystems.getDefault().getPath("Valasz" + name + ".txt");

        Logger logger = new Logger(logFile);

        /////////////////////////////////////////////////////////////////////////////////

        Control control = new Control(ioHandler.readFile());

        // 1. feladat

        SpeedMeter meter = control.getSpeedMeter('A');
    }
}
