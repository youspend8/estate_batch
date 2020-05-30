package kr.co.estate.client;

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
    private static final String BASE_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
    private static final String KEY = "JVt793QxN1mmf3WT4Hd9Xa3s08jDkQGSUEE42CovgbsEvSLtRotfI%2BZ%2F343ZziBHGzCWzPmJSOaOU%2BhRqdx%2BYg%3D%3D";
    private String lawdCd;          //  ex) LAWD_CD=11110
    private String dealYmd;         //  ex) DEAL_YMD=201512

    public URL getApartmentTradePriceURL() {
        URL url = null;
        try {
            url = new URL(new StringBuilder()
                    .append(BASE_URL)
                    .append("?serviceKey=").append(KEY)
                    .append("&LAWD_CD=").append(lawdCd)
                    .append("&DEAL_YMD=").append(dealYmd)
                    .toString());

            System.out.println(">> Request URL :: " + url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
