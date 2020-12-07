package kr.co.estate.dto;

import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;

@Getter
@ToString
public class BaseItemDto {
    @XmlElement(name = "건축년도")
    private int buildYear;

    @XmlElement(name = "지역코드")
    private int regionCode;

    @XmlElement(name = "년")
    private int dealYear;

    @XmlElement(name = "월")
    private int dealMonth;

    @XmlElement(name = "일")
    private int dealDay;

    @XmlElement(name = "층")
    private int floor;

    @XmlElement(name = "법정동")
    private int dong;

    @XmlElement(name = "지번")
    private int jibun;

    @XmlElement(name = "주택유형")
    private int villaType;

    @XmlElement(name = "시군구")
    private int sigungu;
}
