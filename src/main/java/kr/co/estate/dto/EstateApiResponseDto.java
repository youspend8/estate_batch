package kr.co.estate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *  Response format
 *
 *  <response>
 * 	    <header>
 * 	    	<resultCode>00</resultCode>
 * 	    	<resultMsg>NORMAL SERVICE.</resultMsg>
 * 	    </header>
 * 	    <body>
 * 	    	<items>
 * 	    		<item>
 * 	    		    <거래금액> 162,000</거래금액>
 * 	    		    <건축년도>2008</건축년도>
 * 	    		    <년>2020</년>
 * 	    		    <법정동> 사직동</법정동>
 * 	    		    <아파트>광화문풍림스페이스본(101동~105동)</아파트>
 * 	    		    <월>11</월>
 * 	    		    <일>3</일>
 * 	    		    <전용면적>158.99</전용면적>
 * 	    		    <지번>9</지번>
 * 	    		    <지역코드>11110</지역코드>
 * 	    		    <층>10</층>
 * 	    		</item>
 * 	    	</items>
 * 	    	<numOfRows>10</numOfRows>
 * 	    	<pageNo>1</pageNo>
 * 	    	<totalCount>36</totalCount>
 * 	    </body>
 *  </response>
 */
@Getter
@ToString
@XmlRootElement(name = "response")
public class EstateApiResponseDto<T extends BaseItemDto> {
    @JsonProperty("header")
    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name = "body")
    private Body<T> body;

    @Getter
    @ToString
    private static class Header {
        @XmlElement(name = "resultCode")
        private String code;

        @XmlElement(name = "resultMsg")
        private String message;
    }

    @Getter
    @ToString
    private static class Body<T> {
        @XmlElementWrapper(name = "items")
        @XmlElement(name = "item")
        private List<T> items;

        @XmlElement(name = "numOfRows")
        private int count;

        @XmlElement(name = "pageNo")
        private int page;

        @XmlElement(name = "totalCount")
        private int total;
    }
}
