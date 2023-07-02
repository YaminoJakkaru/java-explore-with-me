package ru.practicum.main.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationDto;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping(path = "/admin/compilations")
@Validated
public class AdminCompilationController {

    private final CompilationService compilationService;

    @Autowired
    public AdminCompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.addCompilation(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@Positive @PathVariable Long compId,
                                            @RequestBody UpdateCompilationDto updateCompilationDto) {
        return compilationService.updateCompilation(compId, updateCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@Positive @PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }
}
