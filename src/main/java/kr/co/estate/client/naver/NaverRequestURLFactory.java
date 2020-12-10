package kr.co.estate.client.naver;

import kr.co.estate.config.properties.NaverApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
public class NaverRequestURLFactory {
    private final NaverApiProperties naverApiProperties;

    public String getRequestURL(String query) {
        try {
            return naverApiProperties.getSearchAddress()
                    + String.format("?query=%s", URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpHeaders getRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-NCP-APIGW-API-KEY-ID", naverApiProperties.getClientId());
        headers.add("X-NCP-APIGW-API-KEY", naverApiProperties.getClientSecret());
        return headers;
    }
}
