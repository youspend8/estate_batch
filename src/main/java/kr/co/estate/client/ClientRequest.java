package kr.co.estate.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;

public interface ClientRequest {

    default String getResponse(String url) {
        return getResponse(url, HttpHeaders.EMPTY);
    }

    String getResponse(String url, HttpHeaders headers);

    default JsonNode getResponseJson(String url) {
        return getResponseJson(url, HttpHeaders.EMPTY);
    }

    JsonNode getResponseJson(String url, HttpHeaders headers);
}
