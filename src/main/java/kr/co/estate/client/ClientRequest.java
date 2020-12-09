package kr.co.estate.client;

import org.springframework.http.HttpHeaders;

public interface ClientRequest {

    default String getResponse(String url) {
        return getResponse(url, HttpHeaders.EMPTY);
    }

    String getResponse(String url, HttpHeaders headers);
}
