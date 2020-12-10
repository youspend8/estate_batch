package kr.co.estate.dto;

import lombok.*;
import org.springframework.data.geo.Point;

@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CoordinateDto {
    private double longitude;
    private double latitude;

    public static CoordinateDto of(double longitude, double latitude) {
        return new CoordinateDto(longitude, latitude);
    }

    public Point asEntity() {
        return new Point(longitude, latitude);
    }
}
