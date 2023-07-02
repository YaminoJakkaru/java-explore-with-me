package ru.practicum.main.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Page<Category> findAll(Pageable pageable);

    Category findCategoryById(long id);

    void deleteCategoryById(long categoryId);
}
