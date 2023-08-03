package ru.practicum.main.event.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.stats.client.hit.HitClient;
import ru.practicum.stats.dto.hit.StoredEndpointHitDto;

import java.time.LocalDateTime;

@Component
public class EventHitClient  {

    private final HitClient hitClient;

    @Autowired
    public EventHitClient(HitClient hitClient) {
        this.hitClient = hitClient;
    }

    public void saveEndpoint (String uri, String ip) {
        StoredEndpointHitDto storedEndpointHitDto = new StoredEndpointHitDto()
                .setApp("ewm-main-service")
                .setUri(uri)
                .setIp(ip)
                .setInstant(LocalDateTime.now());
        hitClient.post("", storedEndpointHitDto);
    }

}
