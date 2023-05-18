package ru.practicum.stats.hit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.StoredEndpointHitDto;
import ru.practicum.stats.hit.HitRepository;
import ru.practicum.stats.hit.mapper.HitMapper;


@Service
public final class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Autowired
    public HitServiceImpl(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    @Override
    public void addEndpointHit(StoredEndpointHitDto storedEndpointHitDto) {

        hitRepository.save(HitMapper.toEndpointHit(storedEndpointHitDto));
    }
}
