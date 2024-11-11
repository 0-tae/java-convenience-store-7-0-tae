package store.model;


public class Item {

    private String productName;

    private int quantity;

    public Item(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
