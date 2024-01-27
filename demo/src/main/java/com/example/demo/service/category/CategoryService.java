package com.example.demo.service.category;

import com.example.demo.entity.Category;
import com.example.demo.entity.Income;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface CategoryService {
  List<Category> getAllCategories();

  Page<Category> getCategoriesByPage(int page, int size);

  Category getCategoryById(UUID id);

  void saveCategory(Category category);

  void deleteCategory(UUID id);
}
