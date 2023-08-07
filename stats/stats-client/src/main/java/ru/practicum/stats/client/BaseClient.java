package ru.practicum.stats.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.stats.dto.hit.ViewedEndpointHitDto;

import java.util.List;
import java.util.Map;

public class BaseClient {


    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }


    protected <T> ResponseEntity<List<ViewedEndpointHitDto>> makeAndSendRequest(HttpMethod method, String path,
                                                                                Map<String, Object> parameters,
                                                                                T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<T> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<List<ViewedEndpointHitDto>> statsResponse;
        if (parameters != null) {
            statsResponse = rest.exchange(path, method, requestEntity,
                    new ParameterizedTypeReference<List<ViewedEndpointHitDto>>() {}, parameters);
        } else {
            statsResponse = rest.exchange(path, method, requestEntity,
                    new ParameterizedTypeReference<List<ViewedEndpointHitDto>>() {});
        }
        return prepareGatewayResponse(statsResponse);
    }

    private static ResponseEntity
            <List<ViewedEndpointHitDto>> prepareGatewayResponse(ResponseEntity<List<ViewedEndpointHitDto>> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }
}
