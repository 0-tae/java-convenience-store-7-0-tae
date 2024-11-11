package store.enums;

public enum ItemParserExceptionMessage {

    QUANTITY_NUMBER_FORMAT_INCORRECT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ITEM_FORMAT_INCORRECT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");

    private final String message;

    ItemParserExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }
}
