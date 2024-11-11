package store.enums;


import store.domain.Product;
import store.domain.Promotion;

public enum EntityDataConfig {

    PRODUCTS("products.md", Product.class),
    PROMOTIONS("promotions.md", Promotion.class);

    private final String path;
    private final Class<?> entityType;
    private static final String PREFIX = "./src/main/resources/";

    EntityDataConfig(String path, Class<?> entityType) {
        this.path = PREFIX + path;
        this.entityType = entityType;
    }

    public String getPath() {
        return this.path;
    }

    public Class<?> getEntityType() {
        return this.entityType;
    }

    public static EntityDataConfig findByEntityType(Class<?> givenEntityType) {
        if (givenEntityType.equals(EntityDataConfig.PROMOTIONS.entityType)) {
            return EntityDataConfig.PROMOTIONS;
        }

        if (givenEntityType.equals(EntityDataConfig.PRODUCTS.entityType)) {
            return EntityDataConfig.PRODUCTS;
        }

        throw new IllegalStateException("일치하는 EntityDataConfig가 없습니다.");
    }
}
