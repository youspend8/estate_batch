package kr.co.estate.entity.embedded;

import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Coordinate {
    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    public static Coordinate of(double latitude, double longitude) {
        return new Coordinate(latitude, longitude);
    }

    public Point asEntity() {
        return new Point(latitude, longitude);
    }
}
