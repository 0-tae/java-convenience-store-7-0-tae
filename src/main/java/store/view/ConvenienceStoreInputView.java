package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.Item;
import store.domain.Product;
import store.model.parser.ItemParser;
import store.model.parser.Parser;

import java.util.List;

public class ConvenienceStoreInputView {
    public String input() {
        return Console.readLine();
    }

    public String input(String message) {
        System.out.println(message);
        return Console.readLine();
    }

    public int getInt() {
        try {
            return Integer.parseInt(input());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("범위 외의 입력이 들어왔거나, 정수가 아닌 값이 입력 되었습니다.");
        }
    }

    public List<Item> readItems() {
        while (true) {
            try {
                System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
                return Parser.select(ItemParser.class).parse(input());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String readResponseForInsufficientPromotionPurchase(Product product, int quantity) {
        return readResponse("현재 " + product.getName() + " " + quantity + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
    }

    public String readResponseForAddPromotionQuantity(Product product, int quantity) {
        return readResponse("현재 " + product.getName() + "은(는) " + quantity + "개를  무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
    }

    public String readResponseForMembership() {
        return readResponse("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    public String readResponseForKeepShopping() {
        return readResponse("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
    }

    public String readResponse(String message) {
        while (true) {
            try {
                String input = input(message);

                if (!(input.equals("N") || input.equals("Y"))) {
                    throw new IllegalArgumentException("[ERROR] Y 혹은 N이 입력 되어야 합니다. 다시 입력 해주세요.");
                }

                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
