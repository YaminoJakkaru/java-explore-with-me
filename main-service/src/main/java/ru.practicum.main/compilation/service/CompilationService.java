package ru.practicum.main.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    CompilationDto updateCompilation(long id, UpdateCompilationDto updateCompilationDto);

    void deleteCompilation(long id);

    CompilationDto findCompilationById(long id);

    List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable);
}
