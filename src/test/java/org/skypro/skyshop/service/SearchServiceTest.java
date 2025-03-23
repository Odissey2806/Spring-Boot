package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch_EmptyStorage() {
        // Подготовка
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        // Выполнение
        Collection<SearchResult> results = searchService.search("яблоко");

        // Проверка
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    void testSearch_NoMatchingObjects() {
        // Подготовка
        Product product = new SimpleProduct(UUID.randomUUID(), "Банан", 30);
        Article article = new Article(UUID.randomUUID(), "Факты о бананах", "Бананы богаты калием.");
        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(product, article));

        // Выполнение
        Collection<SearchResult> results = searchService.search("яблоко");

        // Проверка
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    void testSearch_MatchingObjectsFound() {
        // Подготовка
        Product product = new SimpleProduct(UUID.randomUUID(), "Яблоко", 50);
        Article article = new Article(UUID.randomUUID(), "Польза яблок", "Яблоки богаты клетчаткой.");
        when(storageService.getAllSearchables()).thenReturn(List.of(product, article));

        // Выполнение
        Collection<SearchResult> results = searchService.search("яблоко");

        // Проверка
        assertEquals(2, results.size(), "Ожидается 2 результата поиска");
        assertTrue(
                results.stream().anyMatch(result -> result.getName().contains("Яблоко")),
                "Ожидается результат с именем 'Яблоко'"
        );
        assertTrue(
                results.stream().anyMatch(result -> result.getName().contains("Польза яблок")),
                "Ожидается результат с именем 'Польза яблок'"
        );
        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    void testSearch_NullPattern() {
        // Подготовка
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        // Выполнение
        Collection<SearchResult> results = searchService.search(null);

        // Проверка
        assertTrue(results.isEmpty());
        verify(storageService, never()).getAllSearchables();
    }

    @Test
    void testSearch_EmptyPattern() {
        // Подготовка
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        // Выполнение
        Collection<SearchResult> results = searchService.search("");

        // Проверка
        assertTrue(results.isEmpty());
        verify(storageService, never()).getAllSearchables();
    }
}
