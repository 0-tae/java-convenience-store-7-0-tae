package store.util;

import java.util.List;

public class MarkDownObject {
    private final List<String> columns;
    private List<List<String>> raws;

    public MarkDownObject(List<String> columns, List<List<String>> raws) {
        this.columns = columns;
        this.raws = raws;
        validate();
    }

    private void validate() {
        for (List<String> raw : raws) {
            if (raw.size() != columns.size()) {
                throw new IllegalStateException("Column, Raw 사이즈 불일치");
            }
        }
    }

    public List<String> getColumns() {
        return this.columns;
    }

    public List<List<String>> getRaws() {
        return this.raws;
    }

    public List<String> getRaw(int index) {
        return this.raws.get(index);
    }
}
