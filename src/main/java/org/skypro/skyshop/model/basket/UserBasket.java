package org.skypro.skyshop.model.basket;

import java.util.List;

public class UserBasket {
    private final List<BasketItem> basketItems;
    private final int total;

    public UserBasket(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
        this.total = basketItems.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public int getTotal() {
        return total;
    }
}
