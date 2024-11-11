package store.util;

import store.exception.FileManagerExceptionMassage;

import java.io.*;
import java.util.List;

public class FileManager {

    private final BufferedReader reader;
    private final List<String> currentFileContents;
    private final String path;

    public FileManager(String path) {
        try {
            this.path = path;
            this.reader = new BufferedReader(new FileReader(path));
            this.currentFileContents = this.reader.lines().toList();
        } catch (IOException e) {
            throw new IllegalStateException(FileManagerExceptionMassage.FILE_NOT_FOUND.getMessage());
        }
    }

    public List<String> readFileAll() {
        return this.currentFileContents;
    }
}
