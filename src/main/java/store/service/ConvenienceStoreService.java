package store.service;

import store.domain.Product;
import store.domain.Promotion;
import store.model.Item;
import store.model.OrderedProduct;
import store.util.ProductEntityManager;
import store.util.PromotionEntityManager;

import java.util.List;

public class ConvenienceStoreService {
    private final ProductEntityManager productEntityManager;
    private final PromotionEntityManager promotionEntityManager;

    public ConvenienceStoreService() {
        this.productEntityManager = new ProductEntityManager();
        this.promotionEntityManager = new PromotionEntityManager();
    }

    public List<Product> findProductsByItem(Item item) {
        return productEntityManager.findAllByName(item.getProductName());
    }

    public List<Product> findProductsByName(String productName) {
        return productEntityManager.findAllByName(productName);
    }

    public List<Product> findProductsAll() {
        return productEntityManager.findProductsAll();
    }

    public Promotion findPromotionByProduct(Product product) {
        if (product == null) {
            return null;
        }

        return promotionEntityManager.findByProduct(product);
    }

    public OrderedProduct getOrderedProductFromGivenItem(Item givenItem) {
        int orderedQuantity = givenItem.getQuantity();
        List<Product> products = findProductsByItem(givenItem);
        Product promotionProduct = products.stream().filter(Product::hasPromotion).findAny().orElse(null);
        Product product = products.stream().filter(p -> !p.hasPromotion()).findAny().orElse(null);

        Promotion promotion = findPromotionByProduct(promotionProduct);

        return new OrderedProduct(product, promotionProduct, orderedQuantity, promotion);
    }
}
