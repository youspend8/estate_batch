package kr.co.estate;

import kr.co.estate.entity.CityCodeDTO;
import kr.co.estate.repository.CityCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

@Configuration
@RequiredArgsConstructor
@Profile("prod")
public class Beans {
    private final CityCodeRepository cityCodeRepository;

    @Bean(name = "cityCodeQueue")
    public ConcurrentLinkedQueue<CityCodeDTO> cityCodeQueue() {
        List<CityCodeDTO> list = cityCodeRepository.findAllByType("1");

//        return new ConcurrentLinkedQueue<>(list.subList(100, list.size()));
        return new ConcurrentLinkedQueue<>(list);
    }

    @Bean(name = "forkJoinPool")
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(5);
    }

}
