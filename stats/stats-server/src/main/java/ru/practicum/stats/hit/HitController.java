package ru.practicum.stats.hit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.hit.service.HitService;
import ru.practicum.stats.dto.hit.StoredEndpointHitDto;

@RestController
@RequestMapping(path = "/hit")
@Slf4j
public final class HitController {

    private final HitService hitService;

    @Autowired
    public HitController(HitService hitService) {
        this.hitService = hitService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addEndpointHit(@RequestBody StoredEndpointHitDto storedEndpointHitDto) {
        log.info(storedEndpointHitDto.toString());
        hitService.addEndpointHit(storedEndpointHitDto);
    }

}
