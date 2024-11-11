package store.service;

import store.model.Bills;

public class MembershipService {
    private final int MAX_DISCOUNT_VALUE = 8000;
    private final float DISCOUNT_RATE = 0.3f;

    public int discount(Bills bill) {
        int cost = bill.getTotalCostExceptPromotionPurchasedQuantity();
        int discountingValue = (int) (cost * DISCOUNT_RATE);
        return Math.min(discountingValue, MAX_DISCOUNT_VALUE);
    }
}
