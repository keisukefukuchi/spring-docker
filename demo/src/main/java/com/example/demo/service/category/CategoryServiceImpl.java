package com.example.demo.service.category;

// CategoryServiceImpl.java
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        // 作成日時と更新日時を設定
        category.setCreatedAt(LocalDate.now());
        category.setUpdatedAt(LocalDate.now());

        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}

