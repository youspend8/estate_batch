package kr.co.estate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.client.ClientRequest;
import kr.co.estate.client.kakao.KakaoRequestURLFactory;
import kr.co.estate.client.naver.NaverRequestURLFactory;
import kr.co.estate.dto.CoordinateDto;
import kr.co.estate.entity.TradeMasterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoordinateService {
    private final ObjectMapper objectMapper;
    private final ClientRequest clientRequest;
    private final KakaoRequestURLFactory kakaoRequestURLFactory;
    private final NaverRequestURLFactory naverRequestURLFactory;

    public CoordinateDto searchCoordinate(TradeMasterEntity tradeMasterEntity) {
        final String query = String.format("%s %s %s",
                tradeMasterEntity.getLocation().getSigungu(), tradeMasterEntity.getLocation().getDong(),
                tradeMasterEntity.getLocation().getJibun() != null ? tradeMasterEntity.getLocation().getJibun() : "");

        log.debug("searchCoordinate:query ==> {}", query);

        JsonNode resultJson = clientRequest.getResponseJson(
                naverRequestURLFactory.getRequestURL(query), naverRequestURLFactory.getRequestHeader());

        JsonNode addressJson = resultJson
                .findPath("addresses");

        addressJson = addressJson.isArray() ? addressJson.get(0) : addressJson;
        try {
            if (!addressJson.has("x") || !addressJson.has("y")) {
                log.error(query + " : x or y 의 값이 존재하지 않습니다.");
                return CoordinateDto.of(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(query + " :: " + resultJson + " ==> " + tradeMasterEntity);
        }

        CoordinateDto coordinateDto = CoordinateDto.of(addressJson.get("x").asDouble(), addressJson.get("y").asDouble());

        log.debug("searchCoordinate:coordinate ==> {}", coordinateDto);

        return coordinateDto;
    }
}
