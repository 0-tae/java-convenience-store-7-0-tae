package store.model;

import store.domain.Product;
import store.domain.Promotion;

public class OrderedProduct {
    private Product product;
    private Product promotionProduct;
    private Promotion promotion;
    private int orderedQuantity;
    private int rewardQuantity;

    public OrderedProduct(Product product, Product promotionProduct, int quantity, Promotion promotion) {
        this.product = product;
        this.promotionProduct = promotionProduct;
        this.orderedQuantity = quantity;
        this.promotion = promotion;
    }


    public boolean hasSufficientQuantity() {
        int quantity = 0;
        if (product != null) {
            quantity += product.getQuantity();
        }

        if (promotionProduct != null) {
            quantity += promotionProduct.getQuantity();
        }

        return quantity >= orderedQuantity;
    }


    public void setProductQuantity() {
        if (promotionProduct != null) {
            int decreased = -promotionProduct.decrease(orderedQuantity);
            if (decreased > 0) {
                product.decrease(decreased);
            }
            return;
        }

        product.decrease(orderedQuantity);
    }

    public int getCostExceptPromotionPurchasedQuantity() {
        int exceptedCost = 0;

        if (promotion != null) {
            exceptedCost = ((promotion.getBuy() + promotion.getGet()) * rewardQuantity) * promotionProduct.getPrice();
        }

        return getTotalCost() - exceptedCost;
    }

    public int getTotalCost() {
        return orderedQuantity * product.getPrice();
    }

    public int getPromotionDiscount() {
        return rewardQuantity * product.getPrice();
    }

    public void setRewardQuantity(int rewardQuantity) {
        this.rewardQuantity = rewardQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public int getRewardQuantity() {
        return rewardQuantity;
    }

    public Product getPromotionProduct() {
        return promotionProduct;
    }

    public Product getProduct() {
        return product;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }
}
