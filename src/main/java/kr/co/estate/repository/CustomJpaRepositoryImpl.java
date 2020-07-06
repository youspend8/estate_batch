package kr.co.estate.repository;

import kr.co.estate.entity.TradeMasterDTO;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomJpaRepositoryImpl implements CustomJpaRepository<TradeMasterDTO> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllBatch(List<TradeMasterDTO> list) {
        final String sql = "INSERT INTO TRADE_MASTER " +
                "(UID, DEAL_YEAR, DEAL_MONTH, DEAL_DAY, DONG, JIBUN, BUILD_YEAR, REGION_CD, SIGUNGU, FLOOR, AREA, AREA_SUB, AMOUNT, AMOUNT_OPTION, TRADE_TYPE, NAME, VILLA_TYPE, DEAL_DATE, CREATE_DATE) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, current_timestamp())";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TradeMasterDTO tradeMasterDTO = list.get(i);
                ps.setObject(1, RandomString.make(16));
                ps.setObject(2, tradeMasterDTO.getDealYear());
                ps.setObject(3, tradeMasterDTO.getDealMonth());
                ps.setObject(4, tradeMasterDTO.getDealDay());
                ps.setObject(5, tradeMasterDTO.getDong());
                ps.setObject(6, tradeMasterDTO.getJibun());
                ps.setObject(7, tradeMasterDTO.getBuildYear());
                ps.setObject(8, tradeMasterDTO.getRegionCode());
                ps.setObject(9, tradeMasterDTO.getSigungu());
                ps.setObject(10, tradeMasterDTO.getFloor());
                ps.setObject(11, tradeMasterDTO.getArea());
                ps.setObject(12, tradeMasterDTO.getAreaSub());
                ps.setObject(13, tradeMasterDTO.getAmount());
                ps.setObject(14, tradeMasterDTO.getAmountOption());
                ps.setObject(15, tradeMasterDTO.getTradeType().ordinal());
                ps.setObject(16, tradeMasterDTO.getName());
                ps.setObject(17, tradeMasterDTO.getVillaType());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
}
