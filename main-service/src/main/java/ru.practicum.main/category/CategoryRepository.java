package ru.practicum.main.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query("update Category c set c.name = ?1 where c.id = ?2")
    int updateNameById(@NonNull String name, @NonNull long id);


    Page<Category> findAll(Pageable pageable);

    Category findCategoryById(long id);

    int removeCategoryById(long categoryId);
}
