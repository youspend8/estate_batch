package kr.co.estate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.service.ApartmentTradePriceService;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.stream.XMLStreamReader;
import java.util.logging.XMLFormatter;

@SpringBootTest
class EstateApplicationTests {

    @Autowired
    private ApartmentTradePriceService apartmentTradePriceService;

    @Test
    void contextLoads() {
        String result = apartmentTradePriceService.fetchApartmentTradePrice();

        JSONObject resultJson = XML.toJSONObject(result);
        System.out.println(resultJson);
    }
}
