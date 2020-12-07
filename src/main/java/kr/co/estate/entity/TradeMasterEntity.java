package kr.co.estate.entity;

import com.fasterxml.jackson.databind.JsonNode;
import kr.co.estate.constants.TradeType;
import lombok.*;
import net.bytebuddy.utility.RandomString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "TRADE_MASTER")
public class TradeMasterEntity {
    @Id
    @Column(name = "UID")
    private String uid;

    @Column(name = "DEAL_YEAR")
    private Integer dealYear;

    @Column(name = "DEAL_MONTH")
    private Integer dealMonth;

    @Column(name = "DEAL_DAY")
    private Integer dealDay;

    @Column(name = "DEAL_DATE")
    private String dealDate;

    @Column(name = "DONG")
    private String dong;

    @Column(name = "JIBUN")
    private String jibun;

    @Column(name = "BUILD_YEAR")
    private Integer buildYear;

    @Column(name = "REGION_CD")
    private String regionCode;

    @Column(name = "SIGUNGU")
    private String sigungu;

    @Column(name = "FLOOR")
    private Integer floor;

    @Column(name = "AREA")
    private Double area;

    @Column(name = "AREA_SUB")
    private Double areaSub;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "AMOUNT_OPTION")
    private Integer amountOption;

    @Column(name = "TRADE_TYPE")
    private TradeType tradeType;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VILLA_TYPE")
    private String villaType;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createAt;

    public static TradeMasterEntity valueOf(JsonNode jsonNode) {
        TradeMasterEntity tradeMasterEntity = new TradeMasterEntity();
        tradeMasterEntity.setUid(RandomString.make(16));
        if (jsonNode.has("지역코드")) {
            tradeMasterEntity.setRegionCode(jsonNode.get("지역코드").asText());
        }
        if (jsonNode.has("년")) {
            tradeMasterEntity.setDealYear(jsonNode.get("년").asInt());
        }
        if (jsonNode.has("월")) {
            tradeMasterEntity.setDealMonth(jsonNode.get("월").asInt());
        }
        if (jsonNode.has("일")) {
            tradeMasterEntity.setDealDay(jsonNode.get("일").asInt());
        }
        if (jsonNode.has("층")) {
            tradeMasterEntity.setFloor(jsonNode.get("층").asInt());
        }
        if (jsonNode.has("건축년도")) {
            tradeMasterEntity.setBuildYear(jsonNode.get("건축년도").asInt());
        }
        if (jsonNode.has("법정동")) {
            tradeMasterEntity.setDong(jsonNode.get("법정동").asText());
        }
        if (jsonNode.has("지번")) {
            tradeMasterEntity.setJibun(jsonNode.get("지번").asText());
        }
        if (jsonNode.has("주택유형")) {
            tradeMasterEntity.setVillaType(jsonNode.get("주택유형").asText());
        }
        if (jsonNode.has("시군구")) {
            tradeMasterEntity.setSigungu(jsonNode.get("시군구").asText());
        }
        tradeMasterEntity.setAmount(parseAmount(jsonNode));
        tradeMasterEntity.setAmountOption(parseAmountOption(jsonNode));
        tradeMasterEntity.setName(parseName(jsonNode));
        tradeMasterEntity.setArea(parseArea(jsonNode));
        tradeMasterEntity.setAreaSub(parseAreaSub(jsonNode));
        return tradeMasterEntity;
    }

    private static Integer parseAmount(JsonNode jsonNode) {
        String amount = null;
        try {
            if (jsonNode.has("거래금액")) {
                amount = jsonNode.get("거래금액").asText();
            } else if (jsonNode.has("보증금")) {
                amount = jsonNode.get("보증금").asText();
            } else if (jsonNode.has("보증금액")) {
                amount = jsonNode.get("보증금액").asText();
            }
        } catch (Exception ignored) {}
        return amount != null ? Integer.parseInt(amount.replaceAll(",", "")) : null;
    }

    private static Integer parseAmountOption(JsonNode jsonNode) {
        String amount = null;
        try {
            if (jsonNode.has("월세금액")) {
                amount = jsonNode.get("월세금액").asText();
            } else if (jsonNode.has("월세")) {
                amount = jsonNode.get("월세").asText();
            }
        } catch (Exception ignored) {}
        return amount != null ? Integer.parseInt(amount.replaceAll(",", "")) : null;
    }

    private static String parseName(JsonNode jsonNode) {
        try {
            if (jsonNode.has("아파트")) {
                return jsonNode.get("아파트").asText();
            } else if (jsonNode.has("단지")) {
                return jsonNode.get("단지").asText();
            } else if (jsonNode.has("연립다세대")) {
                return jsonNode.get("연립다세대").asText();
            }
        } catch (Exception ignored) {}
        return null;
    }

    private static Double parseArea(JsonNode jsonNode) {
        try {
            if (jsonNode.has("전용면적")) {
                return jsonNode.get("전용면적").asDouble();
            } else if (jsonNode.has("연면적")) {
                return jsonNode.get("연면적").asDouble();
            } else if (jsonNode.has("계약면적")) {
                return jsonNode.get("계약면적").asDouble();
            }
        } catch (Exception ignored) {}
        return null;
    }

    private static Double parseAreaSub(JsonNode jsonNode) {
        try {
            if (jsonNode.has("대지권면적")) {
                return jsonNode.get("대지권면적").asDouble();
            } else if (jsonNode.has("대지면적")) {
                return jsonNode.get("대지면적").asDouble();
            }
        } catch (Exception ignored) {}
        return null;
    }
}
