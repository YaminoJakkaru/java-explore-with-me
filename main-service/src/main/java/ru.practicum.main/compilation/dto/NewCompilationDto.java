package ru.practicum.main.compilation.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.model.Event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class NewCompilationDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    @NotNull
    private  boolean pinned;

    private final List<Long> events = new ArrayList<>();

    public Compilation toCompilation() {
        return new Compilation()
                .setPinned(this.isPinned())
                .setTitle(this.getTitle());
    }
}
