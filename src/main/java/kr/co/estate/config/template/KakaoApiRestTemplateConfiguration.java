package kr.co.estate.config.template;

import kr.co.estate.config.properties.KakaoApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class KakaoApiRestTemplateConfiguration {
    private final KakaoApiProperties kakaoApiProperties;

    @Bean
    public RestTemplate kakaoApiRestTemplate() {
        return new RestTemplateBuilder()
                .rootUri(kakaoApiProperties.getSearchAddress())
                .defaultHeader("Authorization", String.format("KakaoAK %s", kakaoApiProperties.getClientId()))
                .build();
    }
}
