package store.service;

import store.model.OrderedProduct;
import store.domain.Product;
import store.domain.Promotion;

public class PromotionService {
    public void setOrderProductByNotAppliedPromotionPurchase(OrderedProduct orderedProduct, String response) {
        Product promotionProduct = orderedProduct.getPromotionProduct();
        Promotion promotion = orderedProduct.getPromotion();
        int orderedQuantity = orderedProduct.getOrderedQuantity();
        int rewardQuantity = promotion.getAppliedPromotionRewardQuantity(promotionProduct);
        int notAppliedPromotionQuantity = promotion.getNotAppliedPromotionQuantity(promotionProduct, orderedQuantity);
        orderedProduct.setRewardQuantity(rewardQuantity);
        if (response.equals("N")) {
            orderedProduct.setOrderedQuantity(orderedQuantity - notAppliedPromotionQuantity);
        }
        orderedProduct.setProductQuantity();
    }

    public void setOrderProductByAddingPromotionPurchase(OrderedProduct orderedProduct, String response) {
        int orderedQuantity = orderedProduct.getOrderedQuantity();
        Promotion promotion = orderedProduct.getPromotion();
        int rewardQuantity = promotion.getAppliedPromotionRewardQuantity(orderedQuantity);

        orderedProduct.setRewardQuantity(rewardQuantity);

        if (response.equals("Y")) {
            orderedProduct.setRewardQuantity(rewardQuantity + promotion.getGet());
            orderedProduct.setOrderedQuantity(orderedQuantity + promotion.getGet());
        }
        orderedProduct.setProductQuantity();
    }

    public void setOrderProductPromotionPurchase(OrderedProduct orderedProduct) {
        int orderedQuantity = orderedProduct.getOrderedQuantity();
        Promotion promotion = orderedProduct.getPromotion();

        int rewardQuantity = promotion.getAppliedPromotionRewardQuantity(orderedQuantity);

        orderedProduct.setRewardQuantity(rewardQuantity);
        orderedProduct.setProductQuantity();
    }
}
