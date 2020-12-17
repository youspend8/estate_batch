package kr.co.estate.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.config.properties.JsonFilesProperties;
import kr.co.estate.dto.trade.TradeAggsDto;
import kr.co.estate.job.queue.TradeAggregateStepQueue;
import kr.co.estate.mapper.TradeMasterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class TradeAggregateStepConfiguration implements
        ItemReader<List<TradeAggsDto>>, ItemWriter<List<TradeAggsDto>> {
    private final JsonFilesProperties jsonFilesProperties;
    private final ObjectMapper objectMapper;
    private final TradeMasterMapper tradeMasterMapper;
    private final LinkedList<TradeAggregateStepQueue.QueueItem> tradeAggregateStepQueue;

    private TradeAggregateStepQueue.QueueItem queueItem;

    @Override
    public List<TradeAggsDto> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        queueItem = tradeAggregateStepQueue.poll();

        log.info("Read Queue Item ==> {}", queueItem);

        if (queueItem == null) {
            return null;
        }

        log.info("Start Time ==> {}", LocalDateTime.now());

        List<TradeAggsDto> result = tradeMasterMapper
                .aggregateByCity(queueItem.getCityType(), queueItem.getTradeTypeList())
                .stream()
                .map(TradeAggsDto::valueOf)
                .collect(Collectors.toList());

        log.info("End Time ==> {}", LocalDateTime.now());

        return result;
    }

    @Override
    public void write(List<? extends List<TradeAggsDto>> list) throws Exception {
        Path directory = Paths.get(jsonFilesProperties.getAggregationDirectory());

        if (Files.notExists(directory)) {
            Files.createDirectory(directory);
        }

        objectMapper.writeValue(
                new File(jsonFilesProperties.getAggregationPath(queueItem.getCityType(), queueItem.getTradeType())), list.get(0));

        log.info("Queue Item {} save ==> {}", queueItem, list.get(0).size());
    }
}
