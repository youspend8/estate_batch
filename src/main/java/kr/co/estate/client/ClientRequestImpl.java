package kr.co.estate.client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientRequestImpl implements ClientRequest {
    private final RestTemplate restTemplate;

    @Override
    public String getResponse(String url, HttpHeaders headers) {
        log.debug(">> request url ==> " + url);

        ResponseEntity<String> responseMap =
                restTemplate.exchange(URI.create(url), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<String>() {});

        if (responseMap.getStatusCode() == HttpStatus.OK) {
            return responseMap.getBody();
        }

        return null;
    }

    @Override
    public JsonNode getResponseJson(String url, HttpHeaders headers) {
        log.debug("request url ==> {}", url);

        ResponseEntity<JsonNode> responseJson =
                restTemplate.exchange(URI.create(url), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<JsonNode>() {});

        if (responseJson.getStatusCode() == HttpStatus.OK) {
            return responseJson.getBody();
        }

        return null;
    }
}
