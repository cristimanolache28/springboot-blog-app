package com.blog.service;

import com.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    String deleteCategory(Long id);
}
