package kr.co.estate.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ToString
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("naver-api")
public final class NaverApiProperties {
    private final String clientId;
    private final String clientSecret;
    private final String searchAddress;
}
