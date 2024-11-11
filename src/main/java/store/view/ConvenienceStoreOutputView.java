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
        outputLine("===========W 편의점=============");
        outputLine(String.format("%-10s %5s %7s", "상품명", "수량", "금액"));
        products.forEach(product ->
                outputLine(String.format("%-10s %6d %15s"
                        , product.getProduct().getName()
                        , product.getOrderedQuantity()
                        , new DecimalFormat("#,###").format(product.getTotalCost())))
        );

        outputLine("============증\t정=============");
        products.stream().filter(product -> product.getRewardQuantity() >= 1).forEach(product ->
                outputLine(String.format("%-10s %5d"
                        , product.getProduct().getName()
                        , product.getRewardQuantity()))
        );
        outputLine("==============================");
        outputLine(String.format("%-10s %5d %10s"
                , "총구매액"
                , bills.getTotalQuantity()
                , new DecimalFormat("#,###").format(bills.getTotalCost())));

        outputLine(String.format("%-10s %12s"
                , "행사할인"
                , new DecimalFormat("#,###").format(-bills.getTotalRewardDiscount())));

        outputLine(String.format("%-10s %12s"
                , "멤버십할인"
                , new DecimalFormat("#,###").format(-bills.getMembershipDiscount())));

        outputLine(String.format("%-10s %16s"
                ,"내실돈"
                , new DecimalFormat("#,###").format(bills.getTotalFinalPurchasedCost())));
    }
}