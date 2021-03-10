package kr.co.estate.config.template;

import kr.co.estate.config.properties.NaverApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class NaverApiRestTemplateConfiguration {
    private final NaverApiProperties naverApiProperties;

    @Bean
    public RestTemplate naverApiRestTemplate() {
        return new RestTemplateBuilder()
                .rootUri(naverApiProperties.getSearchAddress())
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", naverApiProperties.getClientId())
                .defaultHeader("X-NCP-APIGW-API-KEY", naverApiProperties.getClientSecret())
                .build();
    }
}
