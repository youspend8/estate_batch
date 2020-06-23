package kr.co.estate.repository;

import kr.co.estate.entity.ApartmentTradePriceDTO;
import kr.co.estate.entity.TradeMasterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeMasterRepository extends JpaRepository<TradeMasterDTO, String> {
}
