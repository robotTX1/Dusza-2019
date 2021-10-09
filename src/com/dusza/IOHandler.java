package com.dusza;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IOHandler {
    private Path path;

    public IOHandler(Path path) {
        this.path = path;
    }

    public List<String> readFile() {
        try{
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
