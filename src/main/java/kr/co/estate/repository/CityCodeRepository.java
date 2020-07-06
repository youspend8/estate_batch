package kr.co.estate.repository;

import kr.co.estate.entity.CityCodeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityCodeRepository extends JpaRepository<CityCodeDTO, String> {
    public List<CityCodeDTO> findAllByType(String type);
    public List<CityCodeDTO> findAllByNameIn(List<String> list);
}
