package kr.co.estate.entity;

import com.fasterxml.jackson.databind.JsonNode;
import kr.co.estate.config.UniqueIdGenerator;
import kr.co.estate.constants.TradeType;
import kr.co.estate.entity.embedded.Deal;
import kr.co.estate.entity.embedded.Location;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.geo.Point;

import javax.persistence.*;
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
    @GeneratedValue(generator = UniqueIdGenerator.NAME)
    @GenericGenerator(name = UniqueIdGenerator.NAME, strategy = UniqueIdGenerator.STRATEGY)
    private String uid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BUILD_YEAR", nullable = false)
    private Integer buildYear;

    @Column(name = "FLOOR", length = 3)
    private Integer floor;

    @Column(name = "AREA", nullable = false)
    private Double area;

    @Column(name = "AREA_SUB")
    private Double areaSub;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    @Column(name = "AMOUNT_OPTION")
    private Integer amountOption;

    @Column(name = "TRADE_TYPE", nullable = false, length = 1)
    private TradeType tradeType;

    @Column(name = "VILLA_TYPE")
    private String villaType;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "COORDINATE", nullable = false)
    private Point point;

    @Embedded
    private Deal deal;

    @Embedded
    private Location location;

    public static TradeMasterEntity valueOf(JsonNode jsonNode) {
        TradeMasterEntity tradeMasterEntity = new TradeMasterEntity();

        if (jsonNode.has("층")) {
            tradeMasterEntity.setFloor(jsonNode.get("층").asInt());
        }
        if (jsonNode.has("건축년도")) {
            tradeMasterEntity.setBuildYear(jsonNode.get("건축년도").asInt());
        }
        if (jsonNode.has("주택유형")) {
            tradeMasterEntity.setVillaType(jsonNode.get("주택유형").asText());
        }

        Location location = new Location();

        if (jsonNode.has("지역코드")) {
            location.setRegionCode(jsonNode.get("지역코드").asText().substring(0, 2));
            location.setSigunguCode(jsonNode.get("지역코드").asText().substring(2, 5));
        }
        if (jsonNode.has("법정동")) {
            location.setDong(jsonNode.get("법정동").asText());
        }
        if (jsonNode.has("지번")) {
            location.setJibun(jsonNode.get("지번").asText());
        }
        if (jsonNode.has("시군구")) {
            location.setSigungu(jsonNode.get("시군구").asText());
        }
        tradeMasterEntity.setLocation(location);
        tradeMasterEntity.setAmount(parseAmount(jsonNode));
        tradeMasterEntity.setAmountOption(parseAmountOption(jsonNode));
        tradeMasterEntity.setName(parseName(jsonNode));
        tradeMasterEntity.setArea(parseArea(jsonNode));
        tradeMasterEntity.setAreaSub(parseAreaSub(jsonNode));
        tradeMasterEntity.setDeal(Deal.of(
                jsonNode.has("년") ? jsonNode.get("년").asInt() : 0,
                jsonNode.has("월") ? jsonNode.get("월").asInt() : 0,
                jsonNode.has("일") ? jsonNode.get("일").asInt() : 0));
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
