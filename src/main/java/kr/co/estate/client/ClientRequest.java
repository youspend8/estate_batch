package kr.co.estate.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientRequest {
    private final RestTemplate restTemplate;

    public String getResponse(String url) {
        log.debug(">> request url ==> " + url);

        ResponseEntity<String> responseMap =
                restTemplate.exchange(URI.create(url), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<String>() {});

        if (responseMap.getStatusCode() == HttpStatus.OK) {
            return responseMap.getBody();
        }

//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectionRequestTimeout(5000)
//                .setSocketTimeout(10000)
//                .setConnectTimeout(10000)
//                .build();
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setDefaultRequestConfig(requestConfig)
//                .setMaxConnTotal(60)
//                .setMaxConnPerRoute(60)
//                .build();

        return null;
    }
}
