package kr.co.estate.dto;

import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CoordinateDto {
    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    public static CoordinateDto of(double latitude, double longitude) {
        return new CoordinateDto(latitude, longitude);
    }

    public Point asEntity() {
        return new Point(latitude, longitude);
    }
}
