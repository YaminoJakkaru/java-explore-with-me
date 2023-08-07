package ru.practicum.main.category.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.CategoryRepository;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, EventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        log.info("add category " + categoryDto.getName());
        return categoryRepository.save(categoryDto.toCategory()).toCategoryDto();
    }

    @Transactional
    @Override
    public CategoryDto updateCategory( long id,CategoryDto categoryDto) {
        int response = categoryRepository.updateNameById(categoryDto.getName(), id);
        if (response == 0) {
            throw new NotFoundException("Category with id = " + id + "not found");
        }
        log.info("update category " + id + " now name is " + categoryDto.getName());
        categoryDto.setId(id);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> findCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(Category::toCategoryDto).toList();
    }

    @Override
    public CategoryDto findCategoryById(long id) {
        Category category = categoryRepository.findCategoryById(id);
        if (category == null) {
            throw new NotFoundException("Category with id="+ id +" was not found");
        }
        return category.toCategoryDto();
    }

    @Transactional
    @Override
    public void deleteCategory(long id) {
        if (eventRepository.findFirstEventByCategoryId(id) != null) {
            throw  new DataValidationException("The category is not empty");
        }
        int response =  categoryRepository.removeCategoryById(id);;
        if(response == 0) {
            throw new NotFoundException("User with id = " + id + " not found");
        }
    }
}
