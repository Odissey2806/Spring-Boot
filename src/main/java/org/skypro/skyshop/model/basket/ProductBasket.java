package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ProductBasket {
    private List<Product> products;

    public void addProduct(Product product) {
        products.add(product);
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public boolean removeProduct(UUID productId) {
        return products.removeIf(product -> product.getId().equals(productId));
    }
}