package ru.practicum.main.compilation.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.compilation.model.Compilation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class UpdateCompilationDto {

    private String title;


    private  Boolean pinned;

    private final List<Long> events = new ArrayList<>();


}
