package kr.co.estate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.client.ClientRequest;
import kr.co.estate.client.kakao.KakaoRequestURLFactory;
import kr.co.estate.entity.TradeMasterEntity;
import kr.co.estate.entity.embedded.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoordinateService {
    private final ObjectMapper objectMapper;
    private final ClientRequest clientRequest;
    private final KakaoRequestURLFactory kakaoRequestURLFactory;

    public Coordinate searchCoordinate(TradeMasterEntity tradeMasterEntity) {
        final String query = String.format("%s %s %s",
                tradeMasterEntity.getSigungu(), tradeMasterEntity.getDong(),
                tradeMasterEntity.getJibun() != null ? tradeMasterEntity.getJibun() : "");

        log.info("searchCoordinate:query ==> {}", query);

        JsonNode resultJson;

        try {
            resultJson = objectMapper.readTree(clientRequest.getResponse(kakaoRequestURLFactory
                    .getRequestURL(URLEncoder.encode(query, "UTF-8")), kakaoRequestURLFactory.getRequestHeader()));
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        JsonNode addressJson = resultJson
                .findPath("documents")
                .get(0);

        Coordinate coordinate = Coordinate.of(addressJson.get("x").asDouble(), addressJson.get("y").asDouble());

        log.info("searchCoordinate:coordinate ==> {}", coordinate);

        return Coordinate.of(addressJson.get("x").asDouble(), addressJson.get("y").asDouble());
    }
}
