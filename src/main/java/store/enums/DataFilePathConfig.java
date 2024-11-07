package store.enums;

public enum DataFilePathConfig {

    PRODUCTS_PATH("products.md"),
    PROMOTIONS_PATH("promotions.md");

    private final String path;
    private static final String PREFIX = "./src/main/resources/";

    DataFilePathConfig(String path){
        this.path = PREFIX+path;
    }
    public String getPath(){
        return this.path;
    }
}
