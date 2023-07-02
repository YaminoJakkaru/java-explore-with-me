package ru.practicum.main.publicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Validated
public class PublicCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public PublicCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<CategoryDto> findCategories(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                            @Positive @RequestParam(defaultValue = "10") Integer size) {
        return categoryService.findCategories( PageRequest.of(from > 0 ? from / size : 0, size));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{catId}")
    public CategoryDto findCategory(@PathVariable long catId) {
        return categoryService.findCategoryById(catId);
    }
}
