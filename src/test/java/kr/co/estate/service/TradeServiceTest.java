package kr.co.estate.service;

import kr.co.estate.constants.TradeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;

    @Test
    public void test() {
        tradeService.fetchTradePriceByType("11110", "202011", TradeType.APARTMENT_TRADE);
    }
}
