package ru.practicum.main.compilation.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.CompilationRepository;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationDto;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.service.CompilationService;
import ru.practicum.main.event.client.EventStatsClient;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final EventStatsClient eventStatsClient;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository, EventStatsClient eventStatsClient) {

        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
        this.eventStatsClient = eventStatsClient;
    }

    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findEventByIdIn(newCompilationDto.getEvents());
        if (events.size() != newCompilationDto.getEvents().size()) {
            throw new DataValidationException("One or more events not found");
        }
        Compilation compilation = newCompilationDto.toCompilation();
        events.forEach(compilation::addEvent);
        CompilationDto compilationDto = compilationRepository.save(compilation).toCompilationDto();
        compilationDto.getEvents().forEach(eventStatsClient::setViews);
        return compilationDto;
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(long id, UpdateCompilationDto updateCompilationDto) {
        Compilation compilation = compilationRepository.findCompilationById(id);
        if (compilation == null) {
            throw new NotFoundException("Compilation with id= " + id + " was not found");
        }
        if (!updateCompilationDto.getEvents().isEmpty()) {
            compilation.getEvents().removeIf(event -> !updateCompilationDto.getEvents().contains(event.getId()));
            updateCompilationDto.getEvents()
                    .removeAll(compilation.getEvents().stream().map(Event::getId).collect(Collectors.toList()));
            List<Event> events = eventRepository.findEventByIdIn(updateCompilationDto.getEvents());
            if (events.size() != updateCompilationDto.getEvents().size()) {
                throw new DataValidationException("One or more events not found");
            }
            events.forEach(compilation::addEvent);
        }
        if (updateCompilationDto.getTitle() != null) {
            compilation.setTitle(updateCompilationDto.getTitle());
        }
        if (updateCompilationDto.getPinned() != null) {
            compilation.setPinned(updateCompilationDto.getPinned());
        }
        CompilationDto compilationDto = compilationRepository.save(compilation).toCompilationDto();
        compilationDto.getEvents().forEach(eventStatsClient::setViews);
        return compilationDto;
    }

    @Override
    @Transactional
    public void deleteCompilation(long id) {
        Compilation compilation = compilationRepository.findCompilationById(id);
        if (compilation == null) {
            throw new NotFoundException("Compilation with id= " + id + " was not found");
        }
        compilationRepository.deleteCompilationById(id);
    }

    @Override
    public CompilationDto findCompilationById(long id) {
        Compilation compilation = compilationRepository.findCompilationById(id);
        if (compilation == null) {
            throw new NotFoundException("Compilation with id= " + id + " was not found");
        }
       CompilationDto compilationDto = compilation.toCompilationDto();
        compilationDto.getEvents().forEach(eventStatsClient::setViews);
        return compilationDto;
    }

    @Override
    public List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable) {

        List<CompilationDto> compilationDtos = pinned == null ? compilationRepository.findAll(pageable)
                    .map(Compilation::toCompilationDto).toList() :
                    compilationRepository.findCompilationByPinned(pinned, pageable)
                            .map(Compilation::toCompilationDto).toList();
        compilationDtos.forEach(compilationDto -> compilationDto.getEvents().forEach(eventStatsClient::setViews));
return compilationDtos;
    }
}
