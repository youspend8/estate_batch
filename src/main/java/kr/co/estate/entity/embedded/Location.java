package kr.co.estate.entity.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Column(name = "SIGUNGU")
    private String sigungu;

    @Column(name = "DONG")
    private String dong;

    @Column(name = "JIBUN")
    private String jibun;

    @Column(name = "REGION_CD", length = 2)
    private String regionCode;

    @Column(name = "SIGUNGU_CD", length = 3)
    private String sigunguCode;

    @Column(name = "UMD_CD", length = 5)
    private String umdCode;
}
