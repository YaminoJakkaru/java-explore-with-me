package ru.practicum.stats.stats.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.hit.ViewedEndpointHitDto;
import ru.practicum.stats.hit.model.EndpointHit;
import ru.practicum.stats.repository.HitRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final HitRepository hitRepository;

    @Autowired
    public StatsServiceImpl(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    @Override
    public List<ViewedEndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris,
                                               boolean unique) {
        List<EndpointHit> endpointHits = hitRepository.findHits(start, end, uris);
        List<String> ips = new ArrayList<>();
        Map<String,ViewedEndpointHitDto> viewedEndpointHitDtos = new HashMap<>();
        for (EndpointHit endpointHit: endpointHits) {
            if(unique && ips.contains(endpointHit.getIp())){
                continue;
            }
            ips.add(endpointHit.getIp());
            if (!viewedEndpointHitDtos.containsKey(endpointHit.getUri())) {
                viewedEndpointHitDtos.put(endpointHit.getUri(), endpointHit.toViewedEndpointHitDto());
            }
            viewedEndpointHitDtos.get(endpointHit.getUri()).addHit();
        }
        return new ArrayList<>(viewedEndpointHitDtos.values());
    }
}
