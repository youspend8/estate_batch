package kr.co.estate.client;

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
}
