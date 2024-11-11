package store.controller;

import store.domain.Product;
import store.domain.Promotion;
import store.model.Bills;
import store.model.Item;
import store.model.OrderedProduct;
import store.service.ConvenienceStoreService;
import store.service.MembershipService;
import store.service.PromotionService;
import store.view.ConvenienceStoreInputView;
import store.view.ConvenienceStoreOutputView;

import java.util.List;

public class ConvenienceStoreController {
    private final ConvenienceStoreInputView inputView;
    private final ConvenienceStoreOutputView outputView;
    private final ConvenienceStoreService storeService;
    private final PromotionService promotionService;
    private final MembershipService membershipService;


    public ConvenienceStoreController() {
        this.inputView = new ConvenienceStoreInputView();
        this.outputView = new ConvenienceStoreOutputView();
        this.storeService = new ConvenienceStoreService();
        this.promotionService = new PromotionService();
        this.membershipService = new MembershipService();
    }

    public void run() {
        while (true) {
            printWelcomeMessage();
            Bills bills = new Bills();

            processPurchase(bills);
            membershipApply(bills);
            outputView.outputBills(bills);

            if (!keepShopping()) {
                return;
            }
        }
    }

    private void processPurchase(Bills bills) {
        while (true) {
            try {
                purchase(bills);
                return;
            } catch (IllegalArgumentException e) {
                outputView.outputLine(e.getMessage());
            }
        }
    }

    public void printWelcomeMessage() {
        outputView.outputWelcomeMessage();

        for (Product product : storeService.findProductsAll()) {
            List<Product> productPair = storeService.findProductsByName(product.getName());
            outputView.outputProductStatus(product);

            if (productPair.size() == 1 && productPair.stream().anyMatch(Product::hasPromotion)) {
                outputView.outputProductStatus(product.copy());
            }
        }

        outputView.outputLine();
    }

    public void purchase(Bills bills) {
        List<Item> items = inputView.readItems();

        for (Item item : items) {
            OrderedProduct orderedProduct = storeService.getOrderedProductFromGivenItem(item);

            if (!orderedProduct.hasSufficientQuantity()) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }

            processPromotion(orderedProduct);
            bills.addOrderedProduct(orderedProduct);
        }
    }

    public boolean keepShopping() {
        return inputView.readResponseForKeepShopping().equals("Y");
    }

    public void membershipApply(Bills bills) {
        if (inputView.readResponseForMembership().equals("Y")) {
            int discountedCost = membershipService.discount(bills);
            bills.setMembershipDiscount(discountedCost);
        }
    }

    public void processPromotion(OrderedProduct orderedProduct) {
        if (Promotion.isValid(orderedProduct.getPromotion(), orderedProduct.getPromotionProduct())) {
            checkSpecialCase(orderedProduct);
            return;
        }

        orderedProduct.setProductQuantity();
    }

    private void checkSpecialCase(OrderedProduct orderedProduct) {
        Product promotionProduct = orderedProduct.getPromotionProduct();
        Promotion promotion = orderedProduct.getPromotion();
        int orderedQuantity = orderedProduct.getOrderedQuantity();

        processIfNoSufficientPromotionQuantityForOrder(orderedProduct, promotionProduct, promotion, orderedQuantity);
        processIfOrderedQuantityAwardReceivable(orderedProduct, promotionProduct, promotion, orderedQuantity);
        processIfOtherCase(orderedProduct, promotionProduct, promotion, orderedQuantity);
    }

    private void processIfOtherCase(OrderedProduct orderedProduct, Product promotionProduct, Promotion promotion, int orderedQuantity) {
        if (promotion.isNormalCase(promotionProduct, orderedQuantity)) {
            promotionService.setOrderProductPromotionPurchase(orderedProduct);
        }
    }

    private void processIfOrderedQuantityAwardReceivable(OrderedProduct orderedProduct, Product promotionProduct, Promotion promotion, int orderedQuantity) {
        if (promotion.isOrderedQuantityAwardReceivable(promotionProduct, orderedQuantity)) {
            String r = inputView.readResponseForAddPromotionQuantity(promotionProduct, orderedQuantity);
            promotionService.setOrderProductByAddingPromotionPurchase(orderedProduct, r);
        }
    }

    private void processIfNoSufficientPromotionQuantityForOrder(OrderedProduct orderedProduct, Product promotionProduct, Promotion promotion, int orderedQuantity) {
        if (!promotion.isSufficientPromotionQuantityForOrder(promotionProduct, orderedQuantity)) {
            int notAppliedQuan = promotion.getNotAppliedPromotionQuantity(promotionProduct, orderedQuantity);
            String r = inputView.readResponseForInsufficientPromotionPurchase(promotionProduct, notAppliedQuan);
            promotionService.setOrderProductByNotAppliedPromotionPurchase(orderedProduct, r);
        }
    }
}
