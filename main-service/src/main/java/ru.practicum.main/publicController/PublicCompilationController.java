package ru.practicum.main.publicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Validated
public class PublicCompilationController {

    private final CompilationService compilationService;

    @Autowired
    public PublicCompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<CompilationDto> findCompilationById(@RequestParam(required = false) Boolean pinned,
                                                    @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(defaultValue = "10") Integer size) {
        return compilationService.findCompilation(pinned, PageRequest.of(from > 0 ? from / size : 0, size));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{compId}")
    public  CompilationDto findCompilationById(@Positive @PathVariable long compId) {
        return compilationService.findCompilationById(compId);
    }
}
