package store.enums;

public enum ProductExceptionMessage {

    INCORRECT_PROMOTION_QUANTITY_ACCESS("Promotion이 아닌 Product가 PromotionQuantity 메소드를 호출하였습니다.");

    private final String message;

    ProductExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }
}
