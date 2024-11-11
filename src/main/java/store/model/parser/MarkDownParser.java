package store.model.parser;

import java.util.List;

public class MarkDownParser implements StringParser<List<String>> {

    @Override
    public List<String> parse(String givenInput) {
        List<String> values = List.of(givenInput.split(","));
        validate(values);
        return values;
    }

    private void validate(List<String> given) {
        if (given.size() < 1) {
            throw new IllegalArgumentException();
        }
    }
}
