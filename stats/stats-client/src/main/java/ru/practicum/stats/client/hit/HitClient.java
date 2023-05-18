package ru.practicum.stats.client.hit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.client.BaseClient;
import ru.practicum.stats.dto.StoredEndpointHitDto;

public final class HitClient extends BaseClient {
    private static final String API_PREFIX = "/hit";

    public HitClient(@Value("${stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public  ResponseEntity<Object> post(String path, StoredEndpointHitDto body) {
        return makeAndSendRequest(HttpMethod.POST, path, null, body);
    }

}
