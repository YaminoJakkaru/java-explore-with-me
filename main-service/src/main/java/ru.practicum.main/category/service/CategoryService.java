package ru.practicum.main.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(long id, CategoryDto categoryDto);

    List<CategoryDto> findCategories(Pageable pageable);

    CategoryDto findCategoryById(long id);

    void deleteCategory(long id);
}
