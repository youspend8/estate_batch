package kr.co.estate.service;

import kr.co.estate.client.ClientRequest;
import kr.co.estate.client.EstateApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentTradePriceService {

    private final ClientRequest clientRequest;

    public String fetchApartmentTradePrice() {
        EstateApiClient client = EstateApiClient.builder()
                .lawdCd("11110")
                .dealYmd("202004")
                .build();

        String result = clientRequest.getResponse(client.getApartmentTradePriceURL());

        System.out.println(result);

        return result;
    }
}
