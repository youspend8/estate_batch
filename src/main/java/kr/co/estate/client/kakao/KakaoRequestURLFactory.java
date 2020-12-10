package kr.co.estate.client.kakao;

import kr.co.estate.config.properties.KakaoApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
public class KakaoRequestURLFactory {
    private final KakaoApiProperties kakaoApiProperties;

    public String getRequestURL(String query) {
        try {
            return kakaoApiProperties.getSearchAddress()
                    + String.format("?query=%s", URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpHeaders getRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("KakaoAK %s", kakaoApiProperties.getClientId()));
        return headers;
    }
}
