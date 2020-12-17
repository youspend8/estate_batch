package kr.co.estate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TradeMasterMapper {
    List<HashMap<String, Object>> aggregateByCity(
            @Param("type") int type,
            @Param("tradeTypeList") List<Integer> tradeTypeList);
}
