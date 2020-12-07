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
public class ApartmentTradePriceEntity {
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

    public static ApartmentTradePriceEntity valueOf(JsonNode jsonNode) {
        ApartmentTradePriceEntity apartmentTradePriceEntity = new ApartmentTradePriceEntity();
        if (jsonNode.has("도로명건물부번호코드")) {
            apartmentTradePriceEntity.setRoadNameBubunCd(jsonNode.get("도로명건물부번호코드").asText());
        }
        if (jsonNode.has("법정동지번코드")) {
            apartmentTradePriceEntity.setLandCd(jsonNode.get("법정동지번코드").asText());
        }
        if (jsonNode.has("지역코드")) {
            apartmentTradePriceEntity.setRegionalCode(jsonNode.get("지역코드").asText());
        }
        if (jsonNode.has("년")) {
            apartmentTradePriceEntity.setDealYear(jsonNode.get("년").asText());
        }
        if (jsonNode.has("법정동")) {
            apartmentTradePriceEntity.setDong(jsonNode.get("법정동").asText());
        }
        if (jsonNode.has("일련번호")) {
            apartmentTradePriceEntity.setSerial(jsonNode.get("일련번호").asText());
        }
        if (jsonNode.has("거래금액")) {
            apartmentTradePriceEntity.setDealAmount(jsonNode.get("거래금액").asText());
        }
        if (jsonNode.has("도로명코드")) {
            apartmentTradePriceEntity.setRoadNameCd(jsonNode.get("도로명코드").asText());
        }
        if (jsonNode.has("도로명시군구코드")) {
            apartmentTradePriceEntity.setRoadNameSigunguCd(jsonNode.get("도로명시군구코드").asText());
        }
        if (jsonNode.has("법정동본번코드")) {
            apartmentTradePriceEntity.setBonbun(jsonNode.get("법정동본번코드").asText());
        }
        if (jsonNode.has("도로명일련번호코드")) {
            apartmentTradePriceEntity.setRoadNameSeq(jsonNode.get("도로명일련번호코드").asText());
        }
        if (jsonNode.has("아파트")) {
            apartmentTradePriceEntity.setApartmentName(jsonNode.get("아파트").asText());
        }
        if (jsonNode.has("도로명지상지하코드")) {
            apartmentTradePriceEntity.setRoadNameBasementCd(jsonNode.get("도로명지상지하코드").asText());
        }
        if (jsonNode.has("법정동부번코드")) {
            apartmentTradePriceEntity.setBubun(jsonNode.get("법정동부번코드").asText());
        }
        if (jsonNode.has("지번")) {
            apartmentTradePriceEntity.setJibun(jsonNode.get("지번").asText());
        }
        if (jsonNode.has("도로명")) {
            apartmentTradePriceEntity.setRoadName(jsonNode.get("도로명").asText());
        }
        if (jsonNode.has("월")) {
            apartmentTradePriceEntity.setDealMonth(jsonNode.get("월").asText());
        }
        if (jsonNode.has("법정동읍면동코드")) {
            apartmentTradePriceEntity.setEubmyundongCd(jsonNode.get("법정동읍면동코드").asText());
        }
        if (jsonNode.has("층")) {
            apartmentTradePriceEntity.setFloor(jsonNode.get("층").asText());
        }
        if (jsonNode.has("법정동시군구코드")) {
            apartmentTradePriceEntity.setSigunguCd(jsonNode.get("법정동시군구코드").asText());
        }
        if (jsonNode.has("일")) {
            apartmentTradePriceEntity.setDealDay(jsonNode.get("일").asText());
        }
        if (jsonNode.has("도로명건물본번호코드")) {
            apartmentTradePriceEntity.setRoadNameBonbunCd(jsonNode.get("도로명건물본번호코드").asText());
        }
        if (jsonNode.has("건축년도")) {
            apartmentTradePriceEntity.setBuildYear(jsonNode.get("건축년도").asText());
        }
        if (jsonNode.has("전용면적")) {
            apartmentTradePriceEntity.setAreaForExclusiveUse(jsonNode.get("전용면적").asText());
        }
        return apartmentTradePriceEntity;
    }
}
