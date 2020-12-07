package kr.co.estate.repository;

import kr.co.estate.entity.TradeMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeMasterRepository extends JpaRepository<TradeMasterEntity, String>, CustomJpaRepository<TradeMasterEntity> {
}
