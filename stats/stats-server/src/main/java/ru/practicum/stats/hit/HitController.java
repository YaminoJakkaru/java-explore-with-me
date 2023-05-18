package ru.practicum.stats.hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.hit.service.HitService;
import ru.practicum.stats.dto.StoredEndpointHitDto;

@RestController
@RequestMapping(path = "/hit")
public final class HitController {

    private final HitService hitService;

    @Autowired
    public HitController(HitService hitService) {
        this.hitService = hitService;
    }

    @PostMapping
    public void addEndpointHit(@RequestBody StoredEndpointHitDto storedEndpointHitDto) {
        hitService.addEndpointHit(storedEndpointHitDto);
    }
}
