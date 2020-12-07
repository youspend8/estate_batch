package kr.co.estate.config.properties;

import kr.co.estate.constants.TradeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ToString
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("openapi")
public final class OpenApiProperties {
    private final String key;
    private final String apartmentTrade;
    private final String apartmentRent;
    private final String officeTrade;
    private final String officeRent;
    private final String villaTrade;
    private final String villaRent;
    private final String mantionTrade;
    private final String mantionRent;

    public String getBaseURL(TradeType tradeType) {
        switch (tradeType) {
            case APARTMENT_TRADE:   return apartmentTrade;
            case APARTMENT_RENT:    return apartmentRent;
            case OFFICE_TRADE:      return officeTrade;
            case OFFICE_RENT:       return officeRent;
            case VILLA_TRADE:       return villaTrade;
            case VILLA_RENT:        return villaRent;
            case MANTION_TRADE:     return mantionTrade;
            case MANTION_RENT:      return mantionRent;
        }
        return null;
    }
}
