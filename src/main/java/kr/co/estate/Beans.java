package kr.co.estate;

import kr.co.estate.entity.CityCodeEntity;
import kr.co.estate.job.queue.TradeAggregateStepQueue;
import kr.co.estate.repository.CityCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

@Configuration
@RequiredArgsConstructor
@Profile("prod")
public class Beans {
    private final CityCodeRepository cityCodeRepository;

    @Bean(name = "cityCodeQueue")
    public ConcurrentLinkedQueue<CityCodeEntity> cityCodeQueue() {
        List<CityCodeEntity> list = cityCodeRepository.findAllByType("1");

//        return new ConcurrentLinkedQueue<>(list.subList(0, list.size()));
        return new ConcurrentLinkedQueue<>(list);
    }

    @Bean(name = "tradeAggregateStepQueue")
    public LinkedList<TradeAggregateStepQueue.QueueItem> list() {
        return TradeAggregateStepQueue.getQueue();
    }

    @Bean(name = "forkJoinPool")
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(5);
    }

}
