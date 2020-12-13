package kr.co.estate.entity.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Coordinate {
    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "LATITUDE")
    private Double latitude;
}
