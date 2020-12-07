package kr.co.estate.repository;

import kr.co.estate.entity.TradeMasterEntity;

import java.util.List;

public interface CustomJpaRepository<T> {
    public void saveAllBatch(List<TradeMasterEntity> list);
}
