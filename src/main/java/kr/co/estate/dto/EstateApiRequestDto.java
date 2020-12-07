package kr.co.estate.dto;

import kr.co.estate.constants.TradeType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class EstateApiRequestDto {
    private String lawdCd;          //  ex) LAWD_CD=11110
    private String dealYmd;         //  ex) DEAL_YMD=201512
    private TradeType tradeType;
}
