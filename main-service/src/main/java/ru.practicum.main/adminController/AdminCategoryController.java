package ru.practicum.main.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping(path = "/admin/categories")
@Validated
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto addCategory(@Valid @RequestParam CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@Positive @PathVariable long catId, @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(catId, categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@Positive @PathVariable long catId) {
        categoryService.deleteCategory(catId);
    }
}
