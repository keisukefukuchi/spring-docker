package com.example.demo.service.category;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
  public Page<Category> getCategoriesByPage(int page, int size) {
    PageRequest pageRequest = PageRequest.of(
      page,
      size,
      Sort.by(Sort.Direction.DESC, "updatedAt")
    );

    return categoryRepository.findAll(pageRequest);
  }

  @Override
  public Category getCategoryById(UUID id) {
    return categoryRepository.findById(id).orElse(null);
  }

  @Override
  public void saveCategory(Category category) {
    category.setCreatedAt(LocalDate.now());
    category.setUpdatedAt(LocalDate.now());

    categoryRepository.save(category);
  }

  @Override
  public void deleteCategory(UUID id) {
    categoryRepository.deleteById(id);
  }
}
