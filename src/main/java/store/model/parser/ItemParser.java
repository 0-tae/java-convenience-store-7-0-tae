package store.model.parser;

import store.enums.ItemParserExceptionMessage;
import store.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser implements StringParser<List<Item>> {
    @Override
    public List<Item> parse(String givenInput) {
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(givenInput);

        List<String> items = new ArrayList<>();

        while (matcher.find()) {
            items.add(matcher.group(1));
        }

        return getProductQuantities(items);
    }

    private List<Item> getProductQuantities(List<String> items) {
        itemValidate(items);
        return items.stream().map(this::getProductQuantity).toList();
    }

    private Item getProductQuantity(String item) {
        String[] seperated = item.split("-");

        validate(seperated);

        try {
            String productName = seperated[0];
            int quantity = Integer.parseInt(seperated[1]);
            return new Item(productName, quantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ItemParserExceptionMessage.QUANTITY_NUMBER_FORMAT_INCORRECT.getMessage());
        }
    }

    private void itemValidate(List<String> items) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException(ItemParserExceptionMessage.ITEM_FORMAT_INCORRECT.getMessage());
        }
    }

    private void validate(String[] givenItem) {
        if (givenItem.length != 2) {
            throw new IllegalArgumentException(ItemParserExceptionMessage.ITEM_FORMAT_INCORRECT.getMessage());
        }
    }
}
