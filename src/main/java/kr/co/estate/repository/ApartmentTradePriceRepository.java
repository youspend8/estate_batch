package kr.co.estate.repository;

import kr.co.estate.entity.ApartmentTradePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentTradePriceRepository extends JpaRepository<ApartmentTradePriceEntity, Long> {
}
