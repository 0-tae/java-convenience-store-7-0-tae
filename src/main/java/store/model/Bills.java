package store.model;

import java.util.ArrayList;
import java.util.List;

public class Bills {
    private List<OrderedProduct> orderedProductList;
    private int membershipDiscount;

    public Bills() {
        this.orderedProductList = new ArrayList<>();
    }

    public void addOrderedProduct(OrderedProduct orderedProduct) {
        this.orderedProductList.add(orderedProduct);
    }

    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }


    public int getTotalCostExceptPromotionPurchasedQuantity() {
        return orderedProductList.stream().mapToInt(OrderedProduct::getCostExceptPromotionPurchasedQuantity).sum();
    }

    public int getTotalQuantity() {
        return orderedProductList.stream().mapToInt(OrderedProduct::getOrderedQuantity).sum();
    }

    public int getTotalRewardDiscount() {
        return orderedProductList.stream().mapToInt(OrderedProduct::getPromotionDiscount).sum();
    }

    public int getTotalFinalPurchasedCost() {
        return getTotalCost() - getTotalRewardDiscount() - getMembershipDiscount();
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public void setMembershipDiscount(int membershipDiscount) {
        this.membershipDiscount = membershipDiscount;
    }

    public int getTotalCost() {
        return orderedProductList.stream().mapToInt(OrderedProduct::getTotalCost).sum();
    }
}

