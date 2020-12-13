package kr.co.estate.dto;

import kr.co.estate.entity.embedded.Coordinate;
import lombok.*;

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

    public Coordinate asEntity() {
        return new Coordinate(longitude, latitude);
    }
}
