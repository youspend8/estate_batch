package kr.co.estate.entity;


import kr.co.estate.entity.embedded.Coordinate;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CITY_CODE")
public class CityCodeEntity {
    @Id
    @Column(name = "COD_ID")
    private String id;

    @Column(name = "CD_TYPE")
    private String type;

    @Column(name = "CD_NAME")
    private String name;

    @Column(name = "REGION_CD")
    private String region;

    @Column(name = "SIGUNGU_CD")
    private String sigungu;

    @Column(name = "UMD_CD")
    private String umd;

    @Column(name = "FULLNAME")
    private String fullname;

    @Embedded
    private Coordinate coordinate;
}
