package kr.co.estate.entity.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Deal {
    @Column(name = "DEAL_YEAR", nullable = false, length = 4)
    private Integer dealYear;

    @Column(name = "DEAL_MONTH", nullable = false, length = 2)
    private Integer dealMonth;

    @Column(name = "DEAL_DAY", nullable = false, length = 2)
    private Integer dealDay;

    @Column(name = "DEAL_DATE", nullable = false)
    private LocalDate dealDate;

    public static Deal of(int year, int month) {
        return new Deal(year, month, 0, LocalDate.of(year, month, 1));
    }

    public static Deal of(int year, int month, int day) {
        return new Deal(year, month, day, LocalDate.of(year, month, day));
    }
}
