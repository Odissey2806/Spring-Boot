package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.exception.ProductNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketServiceTest {

    @Mock
    private StorageService storageService;

    @Mock
    private ProductBasket productBasket;

    @InjectMocks
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNonExistentProduct_ThrowsException() {
        // Подготовка
        UUID productId = UUID.randomUUID();
        when(storageService.getProductById(productId)).thenReturn(null);

        // Выполнение и проверка
        assertThrows(ProductNotFoundException.class, () -> {
            basketService.addProductToBasket(productId);
        });

        // Проверка, что метод addProduct не был вызван
        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void testAddExistingProduct_CallsAddProduct() {
        // Подготовка
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Яблоко", 50);
        when(storageService.getProductById(productId)).thenReturn(product);

        // Выполнение
        basketService.addProductToBasket(productId);

        // Проверка
        verify(productBasket, times(1)).addProduct(product);
    }

    @Test
    void testGetUserBasket_EmptyBasket() {
        // Подготовка
        when(productBasket.getProducts()).thenReturn(Collections.emptyList());

        // Выполнение
        Collection<Product> result = basketService.getUserBasket();

        // Проверка
        assertTrue(result.isEmpty(), "Корзина должна быть пустой");
    }

    @Test
    void testGetUserBasket_NonEmptyBasket() {
        // Подготовка
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Яблоко", 50);
        when(productBasket.getProducts()).thenReturn(List.of(product));

        // Выполнение
        Collection<Product> result = basketService.getUserBasket();

        // Проверка
        assertEquals(1, result.size(), "Корзина должна содержать 1 товар");
        assertTrue(result.contains(product), "Корзина должна содержать добавленный товар");
    }

    // Дополнительный тест: удаление товара из корзины
    @Test
    void testRemoveProductFromBasket() {
        // Подготовка
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Яблоко", 50);
        when(productBasket.removeProduct(productId)).thenReturn(true);

        // Выполнение
        boolean isRemoved = basketService.removeProductFromBasket(productId);

        // Проверка
        assertTrue(isRemoved, "Товар должен быть удалён из корзины");
        verify(productBasket, times(1)).removeProduct(productId);
    }

    //Добавление товара с null-идентификатором
    @Test
    void testAddProductWithNullId_ThrowsException() {
        // Проверка, что передача null вызывает исключение
        assertThrows(IllegalArgumentException.class, () -> {
            basketService.addProductToBasket(null);
        });

        // Проверка, что метод addProduct не был вызван
        verify(productBasket, never()).addProduct(any());
    }

    //Удаление товара с null-идентификатором
    @Test
    void testRemoveProductWithNullId_ThrowsException() {
        // Проверка, что передача null вызывает исключение
        assertThrows(IllegalArgumentException.class, () -> {
            basketService.removeProductFromBasket(null);
        });

        // Проверка, что метод removeProduct не был вызван
        verify(productBasket, never()).removeProduct(any());
    }

    //Получение корзины с null-продуктами
    @Test
    void testGetUserBasket_WhenBasketIsNull() {
        // Подготовка
        when(productBasket.getProducts()).thenReturn(null);

        // Выполнение и проверка
        assertThrows(NullPointerException.class, () -> {
            basketService.getUserBasket();
        });
    }

    //Добавление товара, который уже есть в корзине
    @Test
    void testAddProduct_WhenProductAlreadyInBasket() {
        // Подготовка
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Яблоко", 50);
        when(storageService.getProductById(productId)).thenReturn(product);
        when(productBasket.getProducts()).thenReturn(List.of(product));

        // Выполнение
        basketService.addProductToBasket(productId);

        // Проверка
        verify(productBasket, times(1)).addProduct(product);
    }

    //Удаление несуществующего товара из корзины
    @Test
    void testRemoveNonExistentProduct_ReturnsFalse() {
        // Подготовка
        UUID productId = UUID.randomUUID();
        when(productBasket.removeProduct(productId)).thenReturn(false);

        // Выполнение
        boolean isRemoved = basketService.removeProductFromBasket(productId);

        // Проверка
        assertFalse(isRemoved, "Товар не должен быть удалён, так как его нет в корзине");
        verify(productBasket, times(1)).removeProduct(productId);
    }
}
