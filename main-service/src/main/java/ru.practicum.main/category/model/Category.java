package ru.practicum.main.category.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.category.dto.CategoryDto;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    public CategoryDto toCategoryDto() {
        return new  CategoryDto()
                .setId(this.getId())
                .setName(this.getName());
    }
}
