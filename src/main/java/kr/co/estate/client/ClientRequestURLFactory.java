package kr.co.estate.client;

import kr.co.estate.config.properties.OpenApiProperties;
import kr.co.estate.dto.EstateApiRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientRequestURLFactory {
    private final OpenApiProperties openApiProperties;

    public String getRequestURL(EstateApiRequestDto estateApiRequestDto) {
        return openApiProperties.getBaseURL(estateApiRequestDto.getTradeType())
                + String.format("?serviceKey=%s", openApiProperties.getKey())
                + String.format("&LAWD_CD=%s", estateApiRequestDto.getLawdCd())
                + String.format("&DEAL_YMD=%s", estateApiRequestDto.getDealYmd());
    }
}
