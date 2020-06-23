package kr.co.estate.job;

import kr.co.estate.entity.TradeMasterDTO;
import kr.co.estate.repository.TradeMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayDeque;

@Configuration
@RequiredArgsConstructor
public class TradeStepConfiguration {
    private final TradeMasterRepository tradeMasterRepository;
    private final ArrayDeque<TradeMasterDTO> tradeMasterQueue;

    public ItemReader<TradeMasterDTO> reader() {
        return tradeMasterQueue::poll;
    }

    public ItemWriter<? super TradeMasterDTO> writer() {
        return list -> {
            System.out.println(list);
            tradeMasterRepository.saveAll(list);
        };
    }
}
