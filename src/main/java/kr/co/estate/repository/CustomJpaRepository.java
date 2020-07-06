package kr.co.estate.repository;

import kr.co.estate.entity.TradeMasterDTO;

import java.util.List;

public interface CustomJpaRepository<T> {
    public void saveAllBatch(List<TradeMasterDTO> list);
}
