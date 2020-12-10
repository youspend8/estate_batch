package kr.co.estate.repository;

import kr.co.estate.config.UniqueIdGenerator;
import kr.co.estate.entity.TradeMasterEntity;
import lombok.RequiredArgsConstructor;
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
                "   , COORDINATE" +
                "   , DEAL_DATE" +
                "   , REGION_CD" +
                "   , SIGUNGU_CD" +
                "   , UMD_CD" +
                ") VALUES (" +
                "   ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp(), POINT(?, ?), ?, ?, ?, ?" +
                ")";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TradeMasterEntity tradeMasterEntity = list.get(i);
                ps.setObject(1, UniqueIdGenerator.generate());
                ps.setObject(2, tradeMasterEntity.getDeal().getDealYear());
                ps.setObject(3, tradeMasterEntity.getDeal().getDealMonth());
                ps.setObject(4, tradeMasterEntity.getDeal().getDealDay());
                ps.setObject(5, tradeMasterEntity.getLocation().getDong());
                ps.setObject(6, tradeMasterEntity.getLocation().getJibun());
                ps.setObject(7, tradeMasterEntity.getBuildYear());
                ps.setObject(8, tradeMasterEntity.getLocation().getSigungu());
                ps.setObject(9, tradeMasterEntity.getFloor());
                ps.setObject(10, tradeMasterEntity.getArea());
                ps.setObject(11, tradeMasterEntity.getAreaSub());
                ps.setObject(12, tradeMasterEntity.getAmount());
                ps.setObject(13, tradeMasterEntity.getAmountOption());
                ps.setObject(14, tradeMasterEntity.getTradeType().ordinal());
                ps.setObject(15, tradeMasterEntity.getName());
                ps.setObject(16, tradeMasterEntity.getVillaType());
                ps.setObject(17, tradeMasterEntity.getPoint().getX());
                ps.setObject(18, tradeMasterEntity.getPoint().getY());
                ps.setObject(19, tradeMasterEntity.getDeal().getDealDate().toString());
                ps.setObject(20, tradeMasterEntity.getLocation().getRegionCode());
                ps.setObject(21, tradeMasterEntity.getLocation().getSigunguCode());
                ps.setObject(22, tradeMasterEntity.getLocation().getUmdCode());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
}
