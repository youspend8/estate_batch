package kr.co.estate.client.kakao;

import kr.co.estate.config.properties.KakaoApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoRequestURLFactory {
    private final KakaoApiProperties kakaoApiProperties;

    public String getRequestURL(String query) {
        return kakaoApiProperties.getSearchAddress()
                + String.format("?query=%s", query);
    }

    public HttpHeaders getRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("KakaoAK %s", kakaoApiProperties.getClientId()));
        return headers;
    }
}
