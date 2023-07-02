package ru.practicum.main.compilation.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.event.dto.EventShortDto;
import java.util.Set;


@Data
@Accessors(chain = true)
public class CompilationDto {

    private long id;

    private String title;

    private boolean pinned;

    private Set<EventShortDto> events;
}
