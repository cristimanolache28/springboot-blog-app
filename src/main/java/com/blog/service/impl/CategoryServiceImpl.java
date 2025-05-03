package com.blog.service.impl;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.payload.PostDto;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = convertDtoToEntity(categoryDto);
        categoryRepository.save(category);
        return convertToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return  categories.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The category doesn't exist."));

        return convertToDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The category doesn't exist."));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
//        category.setId(id);
        categoryRepository.save(category);

        return convertToDto(category);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The category doesn't exist."));

        categoryRepository.deleteById(id);
        return "The category was deleted with successfully";
    }

    public CategoryDto convertToDto(Category category) {return modelMapper.map(category, CategoryDto.class);}

    public Category convertDtoToEntity(CategoryDto categoryDto) {return  modelMapper.map(categoryDto, Category.class);}
}
