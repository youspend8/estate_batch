package kr.co.estate.common;

public class EstateApiBaseURLFactory {
    private static final String BASE_APARTMENT_TRADE = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";
    private static final String BASE_APARTMENT_RENT = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
    private static final String BASE_OFFICE_TRADE = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiTrade";
    private static final String BASE_OFFICE_RENT = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent";
    private static final String BASE_VILLA_TRADE = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSHTrade";
    private static final String BASE_VILLA_RENT = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSHRent";
    private static final String BASE_MANTION_TRADE = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHTrade";
    private static final String BASE_MANTION_RENT = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent";

    public static String getBaseURL(TradeType tradeType) {
        switch (tradeType) {
            case APARTMENT_TRADE:   return BASE_APARTMENT_TRADE;
            case APARTMENT_RENT:    return BASE_APARTMENT_RENT;
            case OFFICE_TRADE:      return BASE_OFFICE_TRADE;
            case OFFICE_RENT:       return BASE_OFFICE_RENT;
            case VILLA_TRADE:       return BASE_VILLA_TRADE;
            case VILLA_RENT:        return BASE_VILLA_RENT;
            case MANTION_TRADE:     return BASE_MANTION_TRADE;
            case MANTION_RENT:      return BASE_MANTION_RENT;
        }
        return null;
    }
}
