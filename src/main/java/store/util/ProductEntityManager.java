package store.util;

import store.domain.Product;

import java.util.List;

public class ProductEntityManager extends EntityManager {

    private List<Product> products;

    public ProductEntityManager() {
        this.products = findProductsAll();
    }

    public Product findByName(String givenProductName) {
        return products.stream().filter(product -> product.nameIsEqual(givenProductName))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

    public List<Product> findAllByName(String givenProductName) {
        List<Product> result = products.stream().filter(product -> product.nameIsEqual(givenProductName)).toList();

        if (result.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }

        return result;
    }

    public List<Product> findProductsAll() {
        if (this.products != null) {
            return this.products;
        }
        return findAll(Product.class);
    }
}
