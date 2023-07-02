package ru.practicum.main.event.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class Location {
    @NotNull
    private  double lat;
    @NotNull
    private  double lon;
}
