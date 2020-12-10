package kr.co.estate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.client.ClientRequest;
import kr.co.estate.client.kakao.KakaoRequestURLFactory;
import kr.co.estate.entity.TradeMasterEntity;
import kr.co.estate.dto.CoordinateDto;
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

    public CoordinateDto searchCoordinate(TradeMasterEntity tradeMasterEntity) {
        final String query = String.format("%s %s %s",
                tradeMasterEntity.getLocation().getSigungu(), tradeMasterEntity.getLocation().getDong(),
                tradeMasterEntity.getLocation().getJibun() != null ? tradeMasterEntity.getLocation().getJibun() : "");

        log.debug("searchCoordinate:query ==> {}", query);

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

        CoordinateDto coordinateDto = CoordinateDto.of(addressJson.get("x").asDouble(), addressJson.get("y").asDouble());

        log.debug("searchCoordinate:coordinate ==> {}", coordinateDto);

        return CoordinateDto.of(addressJson.get("x").asDouble(), addressJson.get("y").asDouble());
    }
}
