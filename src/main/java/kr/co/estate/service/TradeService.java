package kr.co.estate.service;

import kr.co.estate.client.ClientRequest;
import kr.co.estate.client.ClientRequestURLFactory;
import kr.co.estate.constants.TradeType;
import kr.co.estate.dto.EstateApiRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {
    private final ClientRequest clientRequest;
    private final ClientRequestURLFactory clientRequestURLFactory;

    public String fetchTradePriceByType(String lawdCd, String dealYmd, TradeType tradeType) {
        EstateApiRequestDto estateApiRequestDto = EstateApiRequestDto.builder()
                .tradeType(tradeType)
                .lawdCd(lawdCd)
                .dealYmd(dealYmd)
                .build();

        JSONObject result = new JSONObject();

        String response = clientRequest.getResponse(
                clientRequestURLFactory.getRequestURL(estateApiRequestDto));

        try {
            result = XML.toJSONObject(response);

            log.debug(">> ClientResponse toJson :: " + result);
        } catch (Exception e) {
            log.error(">> Excepted convert XML to JSON :: " + response);
        }

        return result.toString();
    }
}
