package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.annotations.Column;
import store.annotations.EntityConstructor;
import store.annotations.Param;

import java.time.LocalDate;

public class Promotion {
    private static int IDX_SEQUENCE = -1;
    private int idx;

    @Column(name = "name")
    private String name;
    @Column(name = "buy")
    private int buy;
    @Column(name = "get")
    private int get;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @EntityConstructor
    public Promotion(
            @Param(name = "name") String name,
            @Param(name = "buy") int buy,
            @Param(name = "get") int get,
            @Param(name = "start_date") LocalDate startDate,
            @Param(name = "end_date") LocalDate endDate) {
        this.idx = ++IDX_SEQUENCE;
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getAppliedPromotionRewardQuantity(Product promotionProduct) {
        return getAppliedPromotionCount(promotionProduct) * get;
    }

    public int getAppliedPromotionQuantity(Product promotionProduct) {
        return getAppliedPromotionCount(promotionProduct) * (buy + get);
    }

    public int getAppliedPromotionCount(Product promotionProduct) {
        return promotionProduct.getPromotionQuantity() / (buy + get);
    }

    public int getAppliedPromotionRewardQuantity(int orderedQuantity) {
        return (orderedQuantity / (buy + get)) * get;
    }

    public boolean isSufficientPromotionQuantityForOrder(Product product, int orderedQuantity) {
        return product.getPromotionQuantity() >= orderedQuantity;
    }


    public boolean isOrderedQuantityAwardReceivable(Product product, int orderedQuantity) {
        return orderedQuantity % (get + buy) == buy && (product.getPromotionQuantity() - orderedQuantity >= get);
    }

    public boolean isNormalCase(Product product, int orderedQuantity) {
        return isSufficientPromotionQuantityForOrder(product, orderedQuantity) &&
                !isOrderedQuantityAwardReceivable(product, orderedQuantity);
    }

    public int getNotAppliedPromotionQuantity(Product product, int orderedQuantity) {
        return orderedQuantity - getAppliedPromotionQuantity(product);
    }

    public boolean nameIsEqual(Product product) {
        return this.name.equals(product.getPromotion());
    }

    public static boolean isValid(Promotion promotion, Product promotionProduct) {
        return promotion != null && promotion.isValidDate() && promotionProduct != null;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    private boolean isValidDate() {
        LocalDate now = DateTimes.now().toLocalDate();
        return (now.isAfter(startDate) && now.isBefore(endDate)) || now.isEqual(startDate) || now.isEqual(endDate);
    }
}
