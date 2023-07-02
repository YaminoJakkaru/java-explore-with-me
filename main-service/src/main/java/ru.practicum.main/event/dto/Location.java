package ru.practicum.main.event.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Location {
    @NotNull
    private final double lat;
    @NotNull
    private final double lon;
}
