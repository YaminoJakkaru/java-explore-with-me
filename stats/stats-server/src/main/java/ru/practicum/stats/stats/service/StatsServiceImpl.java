package ru.practicum.stats.stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.ViewedEndpointHitDto;
import ru.practicum.stats.stats.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(ru.practicum.stats.stats.StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public List<ViewedEndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris,
                                               boolean unique) {

        if (uris == null){
            return unique ? statsRepository.findViewedEndpointHitDtoWithUniqueIps(start,end)
                    : statsRepository.findViewedEndpointHitDto(start,end);
        }
        return unique ? statsRepository.findViewedEndpointHitDtoWithUniqueIps(start,end,uris)
                : statsRepository.findViewedEndpointHitDto(start,end,uris);
    }
}
