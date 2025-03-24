package org.skypro.skyshop.service;

import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.exception.ProductNotFoundException;

import java.util.Collection;
import java.util.UUID;

public class BasketService {

    private final StorageService storageService;
    private final ProductBasket productBasket;

    public BasketService(StorageService storageService, ProductBasket productBasket) {
        this.storageService = storageService;
        this.productBasket = productBasket;
    }

    public void addProductToBasket(UUID productId) {
        Product product = storageService.getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Товар не найден");
        }
        productBasket.addProduct(product);
    }

    public Collection<Product> getUserBasket() {
        return productBasket.getProducts();
    }

    public boolean removeProductFromBasket(UUID productId) {
        return productBasket.removeProduct(productId);
    }
}
