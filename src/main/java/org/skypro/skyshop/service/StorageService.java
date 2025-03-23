package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.*;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.UUID;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage = new HashMap<>();
    private final Map<UUID, Article> articleStorage = new HashMap<>();

    public StorageService() {
        initializeStorage();
    }

    private void initializeStorage() {
        productStorage.put(UUID.randomUUID(), new SimpleProduct(UUID.randomUUID(), "Яблоко", 50));
        productStorage.put(UUID.randomUUID(), new DiscountedProduct(UUID.randomUUID(), "Банан", 30, 10));
        productStorage.put(UUID.randomUUID(), new FixPriceProduct(UUID.randomUUID(), "Апельсин"));
        productStorage.put(UUID.randomUUID(), new SimpleProduct(UUID.randomUUID(), "Манго", 60));
        productStorage.put(UUID.randomUUID(), new DiscountedProduct(UUID.randomUUID(), "Ананас", 70, 20));
        productStorage.put(UUID.randomUUID(), new FixPriceProduct(UUID.randomUUID(), "Киви"));

        articleStorage.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Польза яблок", "Яблоки богаты клетчаткой и витаминами."));
        articleStorage.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Факты о бананах", "Бананы являются отличным источником калия."));
        articleStorage.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Польза апельсинов", "Апельсины содержат много витамина C."));
    }

    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(new ArrayList<>(productStorage.values()));
        searchables.addAll(new ArrayList<>(articleStorage.values()));
        return searchables;
    }
    // Метод для поиска товара по его идентификатору
    public Product getProductById(UUID productId) {
        return productStorage.get(productId);
    }
}