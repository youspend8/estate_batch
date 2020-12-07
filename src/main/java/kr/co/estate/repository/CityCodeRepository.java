package kr.co.estate.repository;

import kr.co.estate.entity.CityCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityCodeRepository extends JpaRepository<CityCodeEntity, String> {
    public List<CityCodeEntity> findAllByType(String type);
    public List<CityCodeEntity> findAllByNameIn(List<String> list);
}
