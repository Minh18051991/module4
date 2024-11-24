package org.example.library.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Long, Integer> items = new HashMap<>();

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void addItem(Long bookId, int quantity) {
        items.put(bookId, items.getOrDefault(bookId, 0) + quantity);
    }

    public void removeItem(Long bookId) {
        items.remove(bookId);
    }

    public void clear() {
        items.clear();
    }
}