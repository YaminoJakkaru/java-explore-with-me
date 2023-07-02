package ru.practicum.main.category.dto;


import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.category.model.Category;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class CategoryDto {
    private  long id;

    @NotBlank
    private  String name;

    public Category toCategory() {
        return new Category()
                .setName(this.getName());
    }
}
