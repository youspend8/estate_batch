package kr.co.estate.client.estate;

import kr.co.estate.config.properties.OpenApiProperties;
import kr.co.estate.dto.EstateApiRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EstateRequestURLFactory {
    private final OpenApiProperties openApiProperties;

    public String getRequestURL(EstateApiRequestDto estateApiRequestDto) {
        String url = openApiProperties.getBaseURL(estateApiRequestDto.getTradeType())
                + String.format("?serviceKey=%s", openApiProperties.getKey())
                + String.format("&LAWD_CD=%s", estateApiRequestDto.getLawdCd())
                + String.format("&DEAL_YMD=%s", estateApiRequestDto.getDealYmd());

        log.debug("EstateRequestURLFactory.getRequestURL ==> {}", url);

        return url;
    }
}
