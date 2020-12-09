package kr.co.estate.repository;

import kr.co.estate.entity.TradeMasterEntity;
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
public class CustomJpaRepositoryImpl implements CustomJpaRepository<TradeMasterEntity> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllBatch(List<TradeMasterEntity> list) {
        final String sql = "INSERT INTO TRADE_MASTER " +
                "(" +
                "     UID" +
                "   , DEAL_YEAR" +
                "   , DEAL_MONTH" +
                "   , DEAL_DAY" +
                "   , DONG" +
                "   , JIBUN" +
                "   , BUILD_YEAR" +
                "   , REGION_CD" +
                "   , SIGUNGU" +
                "   , FLOOR" +
                "   , AREA" +
                "   , AREA_SUB" +
                "   , AMOUNT" +
                "   , AMOUNT_OPTION" +
                "   , TRADE_TYPE" +
                "   , NAME" +
                "   , VILLA_TYPE" +
                "   , CREATE_DATE" +
                "   , LATITUDE" +
                "   , LONGITUDE" +
                "   , COORDINATE" +
                "   , DEAL_DATE" +
                ") VALUES (" +
                "   ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp(), ?, ?, POINT(?, ?), ?" +
                ")";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TradeMasterEntity tradeMasterEntity = list.get(i);
                ps.setObject(1, RandomString.make(16));
                ps.setObject(2, tradeMasterEntity.getDealYear());
                ps.setObject(3, tradeMasterEntity.getDealMonth());
                ps.setObject(4, tradeMasterEntity.getDealDay());
                ps.setObject(5, tradeMasterEntity.getDong());
                ps.setObject(6, tradeMasterEntity.getJibun());
                ps.setObject(7, tradeMasterEntity.getBuildYear());
                ps.setObject(8, tradeMasterEntity.getRegionCode());
                ps.setObject(9, tradeMasterEntity.getSigungu());
                ps.setObject(10, tradeMasterEntity.getFloor());
                ps.setObject(11, tradeMasterEntity.getArea());
                ps.setObject(12, tradeMasterEntity.getAreaSub());
                ps.setObject(13, tradeMasterEntity.getAmount());
                ps.setObject(14, tradeMasterEntity.getAmountOption());
                ps.setObject(15, tradeMasterEntity.getTradeType().ordinal());
                ps.setObject(16, tradeMasterEntity.getName());
                ps.setObject(17, tradeMasterEntity.getVillaType());
                ps.setObject(18, tradeMasterEntity.getCoordinate().getLongitude());
                ps.setObject(19, tradeMasterEntity.getCoordinate().getLatitude());
                ps.setObject(20, tradeMasterEntity.getCoordinate().getLongitude());
                ps.setObject(21, tradeMasterEntity.getCoordinate().getLatitude());
                ps.setObject(22, String.format("%d-%d-%d",
                        tradeMasterEntity.getDealYear(),
                        tradeMasterEntity.getDealMonth(),
                        tradeMasterEntity.getDealDay()));
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
}
