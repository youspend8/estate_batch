package kr.co.estate.client;

import kr.co.estate.common.EstateApiBaseURLFactory;
import kr.co.estate.common.TradeType;
import lombok.*;

import java.net.MalformedURLException;
import java.net.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EstateApiClient {
    private static final String KEY = "JVt793QxN1mmf3WT4Hd9Xa3s08jDkQGSUEE42CovgbsEvSLtRotfI%2BZ%2F343ZziBHGzCWzPmJSOaOU%2BhRqdx%2BYg%3D%3D";
    private String lawdCd;          //  ex) LAWD_CD=11110
    private String dealYmd;         //  ex) DEAL_YMD=201512
    private TradeType tradeType;

    public URL getTradeURL() {
        URL url = null;
        try {
            url = new URL(new StringBuilder()
                    .append(EstateApiBaseURLFactory.getBaseURL(this.tradeType))
                    .append("?serviceKey=").append(KEY)
                    .append("&LAWD_CD=").append(lawdCd)
                    .append("&DEAL_YMD=").append(dealYmd)
                    .toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
