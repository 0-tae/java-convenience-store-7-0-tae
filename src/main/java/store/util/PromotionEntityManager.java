package store.util;

import store.domain.Product;
import store.domain.Promotion;

import java.util.List;

public class PromotionEntityManager extends EntityManager {

    private List<Promotion> promotions;

    public PromotionEntityManager() {
        this.promotions = findPromotionsAll();
    }

    public Promotion findByProduct(Product givenProduct) {
        return promotions.stream().filter(promotion -> promotion.nameIsEqual(givenProduct)).findFirst().orElse(null);
    }

    public List<Promotion> findPromotionsAll() {
        return findAll(Promotion.class);
    }
}
