package kr.co.estate.service;

import kr.co.estate.client.ClientRequest;
import kr.co.estate.client.EstateApiClient;
import kr.co.estate.common.TradeType;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final ClientRequest clientRequest;

    public String fetchTradePrice(String lawdCd, String dealYmd) {
        EstateApiClient client = EstateApiClient.builder()
                .tradeType(TradeType.APARTMENT_TRADE)
                .lawdCd(lawdCd)
                .dealYmd(dealYmd)
                .build();

        JSONObject result = XML.toJSONObject(clientRequest.getResponse(client.getTradeURL()));

        System.out.println(result);

        return result.toString();
    }

    public String fetchTradePriceByType(String lawdCd, String dealYmd, TradeType tradeType) {
        EstateApiClient client = EstateApiClient.builder()
                .tradeType(tradeType)
                .lawdCd(lawdCd)
                .dealYmd(dealYmd)
                .build();

        JSONObject result = XML.toJSONObject(clientRequest.getResponse(client.getTradeURL()));

        System.out.println(result);

        return result.toString();
    }
}
