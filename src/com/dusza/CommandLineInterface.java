package com.dusza;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CommandLineInterface {
    private Path workDir;

    public CommandLineInterface(Path workDir) {
        this.workDir = workDir;
    }

    public Path start() {
        System.out.println("Válassza ki a bemeneti állományt!");

        List<Path> list = readFiles();

        Scanner input = new Scanner(System.in);
        String optionNumber;

        printOptions(list);

        while(true) {
            optionNumber = input.nextLine();

            try {
                int num = Integer.parseInt(optionNumber);
                if (num > 0 && num <= list.size()) return list.get(num-1);
                if (num == list.size()) return null;
                System.out.println("Érvénytelen opció: " + num);
            } catch (IllegalFormatException e) {
                System.out.println("Érvénytelen opció!");
            }
        }
    }

    private void printOptions(List<Path> list) {
        for(int i=0; i<list.size(); i++) {
            System.out.printf("%d. %s\n", i+1, list.get(i).getFileName());
        }
        System.out.printf("%d. Kilépés\n", list.size()+1);
    }

    private List<Path> readFiles() {
        List<Path> result = new ArrayList<>();

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(workDir)) {
            for(Path p : stream) {
                if(!Files.isDirectory(p)) {
                    result.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
