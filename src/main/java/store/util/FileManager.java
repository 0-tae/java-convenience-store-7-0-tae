package store.util;

import store.exception.FileManagerExceptionMassage;

import java.io.*;
import java.util.List;

public class FileManager {

    private final BufferedReader reader;
    private final String path;

    public FileManager(String path) {
        try {
            this.path = path;
            this.reader = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            throw new IllegalStateException(FileManagerExceptionMassage.FILE_NOT_FOUND.getMessage());
        }
    }

    public List<String> readFileAll() {
        return getFileReader().lines().toList();
    }

    public void writeFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath()))) {
            writer.write(content + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException(FileManagerExceptionMassage.FILE_WRITE_ERROR.getMessage());
        }
    }

    public void writeFileAll(List<String> contents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath()))) {
            for (String content : contents) {
                writer.write(content + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            throw new IllegalStateException(FileManagerExceptionMassage.FILE_WRITE_ERROR.getMessage());
        }
    }
    private String getPath(){
        return this.path;
    }
    private BufferedReader getFileReader() {
        return this.reader;
    }

}
