package store.util;

import store.model.parser.MarkDownParser;
import store.model.parser.Parser;

import java.util.List;

public class MarkDownReader {
    private final String path;
    private final FileManager fileManager;

    public MarkDownReader(String path) {
        this.path = path;
        this.fileManager = new FileManager(path);
    }

    public MarkDownObject read() {
        List<String> fileContents = this.fileManager.readFileAll();

        List<String> columns = getColumns(fileContents);
        List<List<String>> raws = getValues(fileContents);

        return new MarkDownObject(columns, raws);
    }

    private String getPath() {
        return this.path;
    }

    private List<String> getColumns(List<String> givenFileContents) {
        if (givenFileContents.isEmpty()) {
            throw new IllegalStateException("Column이 비어있습니다");
        }

        return parse(givenFileContents.getFirst());
    }

    private List<List<String>> getValues(List<String> givenFileContents) {
        int size = givenFileContents.size();

        if (size < 2) {
            throw new IllegalStateException("Value가 비어있습니다");
        }

        return givenFileContents.subList(1, size).stream().map(this::parse).toList();
    }

    private List<String> parse(String givenFileContentLine) {
        return Parser.select(MarkDownParser.class).parse(givenFileContentLine);
    }
}
