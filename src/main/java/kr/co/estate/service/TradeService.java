package kr.co.estate.service;

import kr.co.estate.client.ClientRequest;
import kr.co.estate.client.estate.EstateRequestURLFactory;
import kr.co.estate.constants.TradeType;
import kr.co.estate.dto.EstateApiRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {
    private final ClientRequest clientRequest;
    private final EstateRequestURLFactory estateRequestURLFactory;

    @Transactional
    public String fetchTradePriceByType(String lawdCd, String dealYmd, TradeType tradeType) {
        EstateApiRequestDto estateApiRequestDto = EstateApiRequestDto.builder()
                .tradeType(tradeType)
                .lawdCd(lawdCd)
                .dealYmd(dealYmd)
                .build();

        JSONObject result = new JSONObject();

        String response = clientRequest.getResponse(
                estateRequestURLFactory.getRequestURL(estateApiRequestDto));

        try {
            result = XML.toJSONObject(response);

            log.debug(">> ClientResponse toJson :: " + result);
        } catch (Exception e) {
            log.error(">> Excepted convert XML to JSON :: " + response);
        }

        return result.toString();
    }
}
