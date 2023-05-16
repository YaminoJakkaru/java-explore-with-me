package ru.practicum.stats.stats.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.ViewedEndpointHitDto;
import ru.practicum.stats.stats.StatsRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(ru.practicum.stats.stats.StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public List<ViewedEndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, Optional<String []> uris, boolean unique) {

        if (uris.isEmpty()){
            return unique ? statsRepository.findViewedEndpointHitDtoWithUniqueIps(start,end)
                    : statsRepository.findViewedEndpointHitDto(start,end);
        }
        return unique ? statsRepository.findViewedEndpointHitDtoWithUniqueIps(start,end,uris.get())
                : statsRepository.findViewedEndpointHitDto(start,end,uris.get());
    }
}
