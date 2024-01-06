package com.example.demo.service.category;

// CategoryService.java
import com.example.demo.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(UUID id);

    void saveCategory(Category category);

    void deleteCategory(UUID id);
}
