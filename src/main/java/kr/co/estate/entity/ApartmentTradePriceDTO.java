package kr.co.estate.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="APARTMENT_TRADE_MASTER")
public class ApartmentTradePriceDTO {
    @Id
    @Column(name="UID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long uid;
    @Column(name="DEAL_AMOUNT")
    private String dealAmount;
    @Column(name="BUILD_YEAR")
    private String buildYear;
    @Column(name="DEAL_YEAR")
    private String dealYear;
    @Column(name="ROAD_NAME")
    private String roadName;
    @Column(name="ROAD_NAME_BONBUN_CD")
    private String roadNameBonbunCd;
    @Column(name="ROAD_NAME_BUBUN_CD")
    private String roadNameBubunCd;
    @Column(name="ROAD_NAME_SIGUNGU_CD")
    private String roadNameSigunguCd;
    @Column(name="ROAD_NAME_SEQ")
    private String roadNameSeq;
    @Column(name="ROAD_NAME_BASEMENT_CD")
    private String roadNameBasementCd;
    @Column(name="ROAD_NAME_CD")
    private String roadNameCd;
    @Column(name="DONG")
    private String dong;
    @Column(name="BONBUN")
    private String bonbun;
    @Column(name="BUBUN")
    private String bubun;
    @Column(name="SIGUNGU_CD")
    private String sigunguCd;
    @Column(name="EUBMYUNDONG_CD")
    private String eubmyundongCd;
    @Column(name="LAND_CD")
    private String landCd;
    @Column(name="APARTMENT_NAME")
    private String apartmentName;
    @Column(name="DEAL_MONTH")
    private String dealMonth;
    @Column(name="DEAL_DAY")
    private String dealDay;
    @Column(name="AREA_FOR_EXCLUSIVE_USE")
    private String areaForExclusiveUse;
    @Column(name="JIBUN")
    private String jibun;
    @Column(name="REGIONAL_CODE")
    private String regionalCode;
    @Column(name="FLOOR")
    private String floor;
    @Column(name="SERIAL")
    private String serial;

    public static ApartmentTradePriceDTO valueOf(JsonNode jsonNode) {
        ApartmentTradePriceDTO apartmentTradePriceDTO = new ApartmentTradePriceDTO();
        if (jsonNode.has("도로명건물부번호코드")) {
            apartmentTradePriceDTO.setRoadNameBubunCd(jsonNode.get("도로명건물부번호코드").asText());
        }
        if (jsonNode.has("법정동지번코드")) {
            apartmentTradePriceDTO.setLandCd(jsonNode.get("법정동지번코드").asText());
        }
        if (jsonNode.has("지역코드")) {
            apartmentTradePriceDTO.setRegionalCode(jsonNode.get("지역코드").asText());
        }
        if (jsonNode.has("년")) {
            apartmentTradePriceDTO.setDealYear(jsonNode.get("년").asText());
        }
        if (jsonNode.has("법정동")) {
            apartmentTradePriceDTO.setDong(jsonNode.get("법정동").asText());
        }
        if (jsonNode.has("일련번호")) {
            apartmentTradePriceDTO.setSerial(jsonNode.get("일련번호").asText());
        }
        if (jsonNode.has("거래금액")) {
            apartmentTradePriceDTO.setDealAmount(jsonNode.get("거래금액").asText());
        }
        if (jsonNode.has("도로명코드")) {
            apartmentTradePriceDTO.setRoadNameCd(jsonNode.get("도로명코드").asText());
        }
        if (jsonNode.has("도로명시군구코드")) {
            apartmentTradePriceDTO.setRoadNameSigunguCd(jsonNode.get("도로명시군구코드").asText());
        }
        if (jsonNode.has("법정동본번코드")) {
            apartmentTradePriceDTO.setBonbun(jsonNode.get("법정동본번코드").asText());
        }
        if (jsonNode.has("도로명일련번호코드")) {
            apartmentTradePriceDTO.setRoadNameSeq(jsonNode.get("도로명일련번호코드").asText());
        }
        if (jsonNode.has("아파트")) {
            apartmentTradePriceDTO.setApartmentName(jsonNode.get("아파트").asText());
        }
        if (jsonNode.has("도로명지상지하코드")) {
            apartmentTradePriceDTO.setRoadNameBasementCd(jsonNode.get("도로명지상지하코드").asText());
        }
        if (jsonNode.has("법정동부번코드")) {
            apartmentTradePriceDTO.setBubun(jsonNode.get("법정동부번코드").asText());
        }
        if (jsonNode.has("지번")) {
            apartmentTradePriceDTO.setJibun(jsonNode.get("지번").asText());
        }
        if (jsonNode.has("도로명")) {
            apartmentTradePriceDTO.setRoadName(jsonNode.get("도로명").asText());
        }
        if (jsonNode.has("월")) {
            apartmentTradePriceDTO.setDealMonth(jsonNode.get("월").asText());
        }
        if (jsonNode.has("법정동읍면동코드")) {
            apartmentTradePriceDTO.setEubmyundongCd(jsonNode.get("법정동읍면동코드").asText());
        }
        if (jsonNode.has("층")) {
            apartmentTradePriceDTO.setFloor(jsonNode.get("층").asText());
        }
        if (jsonNode.has("법정동시군구코드")) {
            apartmentTradePriceDTO.setSigunguCd(jsonNode.get("법정동시군구코드").asText());
        }
        if (jsonNode.has("일")) {
            apartmentTradePriceDTO.setDealDay(jsonNode.get("일").asText());
        }
        if (jsonNode.has("도로명건물본번호코드")) {
            apartmentTradePriceDTO.setRoadNameBonbunCd(jsonNode.get("도로명건물본번호코드").asText());
        }
        if (jsonNode.has("건축년도")) {
            apartmentTradePriceDTO.setBuildYear(jsonNode.get("건축년도").asText());
        }
        if (jsonNode.has("전용면적")) {
            apartmentTradePriceDTO.setAreaForExclusiveUse(jsonNode.get("전용면적").asText());
        }
        return apartmentTradePriceDTO;
    }
}
