package kr.co.estate.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import kr.co.estate.dto.BaseItemDto;
import kr.co.estate.dto.EstateApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientRequest {
    private final XmlMapper xmlMapper;
    private final RestTemplate restTemplate;

    public String getResponse(String url) {
        log.debug(">> request url ==> " + url);

        ResponseEntity<String> responseMap =
                restTemplate.exchange(URI.create(url), HttpMethod.GET, HttpEntity.EMPTY, String.class);

        System.out.println("responseEntity :: " + responseMap);
        System.out.println("responseEntity :: " + responseMap.getBody());

        ;
        try {
            System.out.println(xmlMapper.convertValue(responseMap.getBody(), new ParameterizedTypeReference<Map<String, Object>>() {}));
        } catch (Exception e) {
            e.printStackTrace();
        }
//
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
