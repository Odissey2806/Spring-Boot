package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.search.Searchable;
import java.util.UUID;

public class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String content;

    public Article(UUID id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getSearchTerm() {
        return title + " " + content;
    }

    @Override
    public String getContentType() {
        return "Статья";
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String toString() {
        return title + ": " + content;
    }

}