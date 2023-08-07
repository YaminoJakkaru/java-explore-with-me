package ru.practicum.main.category.dto;


import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.category.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class CategoryDto {
    private  long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private  String name;

    public Category toCategory() {
        return new Category()
                .setName(this.getName());
    }
}
