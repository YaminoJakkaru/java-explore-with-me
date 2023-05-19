package ru.practicum.stats.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.dto.ViewedEndpointHitDto;
import ru.practicum.stats.hit.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public  interface StatsRepository extends JpaRepository<EndpointHit, Long> {


    @Query(value = "select new  ru.practicum.stats.dto.ViewedEndpointHitDto(e.app,e.uri ,count(e.ip))" +
            " from EndpointHit  as e where e.instant between ?1 and ?2 group by e.app, e.uri order by count(e.ip) desc")
     List<ViewedEndpointHitDto> findViewedEndpointHitDto(LocalDateTime start, LocalDateTime end);
    @Query(value = "select  new  ru.practicum.stats.dto.ViewedEndpointHitDto(e.app, e.uri , count(e.ip))" +
            " from EndpointHit  as e where e.instant between ?1 and ?2 and e.uri in ?3" +
            " group by e.app, e.uri order by count(e.ip) desc")
    List<ViewedEndpointHitDto> findViewedEndpointHitDto(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query(value = "select new  ru.practicum.stats.dto.ViewedEndpointHitDto(e.app, e.uri , count(distinct(e.ip))) " +
            " from EndpointHit  as e where e.instant between ?1 and ?2 " +
            "group by e.app, e.uri order by count(distinct(e.ip)) desc")
    List<ViewedEndpointHitDto> findViewedEndpointHitDtoWithUniqueIps(LocalDateTime start, LocalDateTime end);
    @Query(value = "select   new  ru.practicum.stats.dto.ViewedEndpointHitDto(e.app, e.uri , count(distinct(e.ip)))" +
            " from EndpointHit  as e where e.instant between ?1 and ?2 and e.uri in ?3 " +
            "group by e.app, e.uri order by count(distinct(e.ip)) desc")
    List<ViewedEndpointHitDto> findViewedEndpointHitDtoWithUniqueIps(LocalDateTime start, LocalDateTime end,
                                                                     List<String> uris);
}
