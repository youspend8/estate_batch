package kr.co.estate.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.constants.TradeType;
import kr.co.estate.entity.CityCodeEntity;
import kr.co.estate.entity.TradeMasterEntity;
import kr.co.estate.repository.CityCodeRepository;
import kr.co.estate.repository.TradeMasterRepository;
import kr.co.estate.service.CoordinateService;
import kr.co.estate.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
@RequiredArgsConstructor
@Profile("prod")
@Slf4j
public class TradeStepConfiguration implements ItemReader<List<TradeMasterEntity>>, ItemWriter<List<TradeMasterEntity>> {
    private final TradeMasterRepository tradeMasterRepository;
    private final ConcurrentLinkedQueue<CityCodeEntity> cityCodeQueue;
    private final ForkJoinPool forkJoinPool;
    private final TradeService tradeService;
    private final CoordinateService coordinateService;
    private final CityCodeRepository cityCodeRepository;

    @Override
    public List<TradeMasterEntity> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        CityCodeEntity cityCodeEntity = cityCodeQueue.poll();

        if (cityCodeEntity == null) {
            return null;
        }

        log.info("cityCodeDTO ==> {}", cityCodeEntity);

        String[] period = {
//                  "202005"
//                , "202004"
//                , "202003"
//                , "202002"
//                , "202001"
                "201912"
        };

        List<Callable<List<TradeMasterEntity>>> callableList = new ArrayList<>();

        List<TradeType> tradeTypeList = Arrays.asList(TradeType.values());

        for (String dealYmd : period) {
            List<Callable<List<TradeMasterEntity>>> temp = tradeTypeList.stream()
                    .map(x -> this.callable(cityCodeEntity, dealYmd, x))
                    .collect(Collectors.toList());
            callableList.addAll(temp);
        }

        List<Future<List<TradeMasterEntity>>> futureList = forkJoinPool.invokeAll(callableList);

        List<TradeMasterEntity> returnList = new ArrayList<>();

        futureList.forEach(x -> {
            if (x.isDone()) {
                try {
                    returnList.addAll(x.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        return returnList;
    }

    @Override
    public void write(List<? extends List<TradeMasterEntity>> list) throws Exception {
        log.info("count ==> {}", list.get(0).size());
        tradeMasterRepository.saveAllBatch(list.get(0));
    }

    public Callable<List<TradeMasterEntity>> callable(CityCodeEntity cityCodeEntity, String dealYmd, TradeType tradeType) {
        return () -> {
            final String lawdCd = cityCodeEntity.getRegion() + cityCodeEntity.getSigungu();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper
                    .readTree(tradeService.fetchTradePriceByType(lawdCd, dealYmd, tradeType))
                    .findPath("item");

            return StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(TradeMasterEntity::valueOf)
                    .peek(entity -> {
                        entity.setTradeType(tradeType);
                        entity.getLocation().setSigungu(cityCodeEntity.getName());
                        entity.getLocation().setUmdCode(cityCodeRepository
                                .findByRegionAndSigunguAndName(
                                        cityCodeEntity.getRegion(), cityCodeEntity.getSigungu(), entity.getLocation().getDong())
                                .getUmd());
                        entity.setPoint(coordinateService.searchCoordinate(entity).asEntity());
                    })
                    .collect(Collectors.toList());
        };
    }
}
