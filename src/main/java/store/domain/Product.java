package store.domain;

import store.annotations.Column;
import store.annotations.EntityConstructor;
import store.annotations.Param;
import store.enums.ProductExceptionMessage;
import store.exception.LacksOfPromotionQuantityException;

import java.text.DecimalFormat;
import java.util.Objects;

public class Product {
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "promotion")
    private String promotion;

    @EntityConstructor
    public Product(
            @Param(name = "name") String name,
            @Param(name = "price") int price,
            @Param(name = "quantity") int quantity,
            @Param(name = "promotion") String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Product copy() {
        return new Product(name, price, 0, null);
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getPromotion() {
        if (!hasPromotion()) {
            return "";
        }

        return this.promotion;
    }

    public int getPromotionQuantity() {
        if (hasPromotion()) {
            return this.quantity;
        }

        throw new IllegalStateException(ProductExceptionMessage.INCORRECT_PROMOTION_QUANTITY_ACCESS.getMessage());
    }

    public boolean hasPromotion() {
        return this.promotion != null;
    }

    public boolean nameIsEqual(String otherProductName) {
        return Objects.equals(this.name, otherProductName);
    }

    public int decrease(int quantity) {
        int decreased = this.quantity - quantity;
        this.quantity = Math.max(0, decreased);
        return decreased;
    }
}
