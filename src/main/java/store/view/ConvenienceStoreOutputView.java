package store.view;

import store.domain.Product;
import store.model.Bills;
import store.model.OrderedProduct;

import java.text.DecimalFormat;
import java.util.List;

public class ConvenienceStoreOutputView {
    public void outputLine(String msg) {
        System.out.println(msg);
    }

    public void outputLine() {
        System.out.println();
    }

    public void output(String msg) {
        System.out.print(msg);
    }

    public void outputWelcomeMessage() {
        outputLine("안녕하세요. W편의점입니다.");
        outputLine("현재 보유하고 있는 상품입니다.");
        outputLine();
    }

    public void outputProductStatus(Product product) {
        String name = product.getName();
        String price = new DecimalFormat("#,###").format(product.getPrice()) + "원";
        String quantity = product.getQuantity() + "개";
        String promotion = product.getPromotion();

        if (product.getQuantity() == 0) {
            quantity = "재고 없음";
        }

        outputLine("- " + name + " " + price + " " + quantity + " " + promotion);
    }

    public void outputBills(Bills bills) {
        List<OrderedProduct> products = bills.getOrderedProductList();
        outputPurchasedList(products);
        outputRewardList(products);
        outputTotalCost(bills);
        outputPromotionDiscountCost(bills);
        outputMembershipDiscount(bills);
        outputPurchaseCost(bills);
    }

    private void outputPurchaseCost(Bills bills) {
        outputLine(String.format("%-10s %16s"
                ,"내실돈"
                , new DecimalFormat("#,###").format(bills.getTotalFinalPurchasedCost())));
    }

    private void outputMembershipDiscount(Bills bills) {
        outputLine(String.format("%-10s %16s"
                , "멤버십할인"
                , new DecimalFormat("#,###").format(-bills.getMembershipDiscount())));
    }

    private void outputPromotionDiscountCost(Bills bills) {
        outputLine(String.format("%-10s %16s"
                , "행사할인"
                , new DecimalFormat("#,###").format(-bills.getTotalRewardDiscount())));
    }

    private void outputTotalCost(Bills bills) {
        outputLine(String.format("%-10s %5d %10s"
                , "총구매액"
                , bills.getTotalQuantity()
                , new DecimalFormat("#,###").format(bills.getTotalCost())));
    }

    private void outputRewardList(List<OrderedProduct> products) {
        outputLine("============증\t정=============");
        products.stream().filter(product -> product.getRewardQuantity() >= 1).forEach(product ->
                outputLine(String.format("%-10s %5d"
                        , product.getProduct().getName()
                        , product.getRewardQuantity()))
        );
        outputLine("==============================");
    }

    private void outputPurchasedList(List<OrderedProduct> products) {
        outputLine("===========W 편의점=============");
        outputLine(String.format("%-10s %5s %7s", "상품명", "수량", "금액"));
        products.forEach(product ->
                outputLine(String.format("%-10s %5d %10s"
                        , product.getProduct().getName()
                        , product.getOrderedQuantity()
                        , new DecimalFormat("#,###").format(product.getTotalCost())))
        );
    }
}