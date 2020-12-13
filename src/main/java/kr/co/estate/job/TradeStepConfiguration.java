package kr.co.estate.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.constants.TradeType;
import kr.co.estate.entity.CityCodeEntity;
import kr.co.estate.entity.TradeMasterEntity;
import kr.co.estate.entity.embedded.Deal;
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
import java.util.Collections;
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
    private CityCodeEntity cityCodeEntity;
    private final ObjectMapper objectMapper;

    @Override
    public List<TradeMasterEntity> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        cityCodeEntity = cityCodeQueue.poll();

        if (cityCodeEntity == null) {
            return null;
        }

        List<String> period = Arrays.asList(
                    "202011"
//                    "202010"
//                  "202005"
//                , "202004"
//                , "202003"
//                , "202002"
//                , "202001"
        );

        log.info("Read ==> {}, {}({})",
                String.join("/", period),
                cityCodeEntity.getRegion() + cityCodeEntity.getSigungu(),
                cityCodeEntity.getFullname());

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
        log.info("{} save ==> {}", cityCodeEntity.getFullname(),list.get(0).size());
        tradeMasterRepository.saveAllBatch(list.get(0));
    }

    public Callable<List<TradeMasterEntity>> callable(CityCodeEntity cityCodeEntity, String dealYmd, TradeType tradeType) {
        return () -> {
            final String lawdCd = cityCodeEntity.getRegion() + cityCodeEntity.getSigungu();

            JsonNode jsonNode = objectMapper
                    .readTree(tradeService.fetchTradePriceByType(lawdCd, dealYmd, tradeType))
                    .findPath("item");

            if (jsonNode.isObject()) {
                return Collections.singletonList(setInformation(jsonNode, cityCodeEntity, dealYmd, tradeType));
            } else {
                return StreamSupport.stream(jsonNode.spliterator(), false)
                        .map(json -> setInformation(json, cityCodeEntity, dealYmd, tradeType))
                        .collect(Collectors.toList());
            }
        };
    }

    private TradeMasterEntity setInformation(JsonNode jsonNode, CityCodeEntity cityCodeEntity, String dealYmd, TradeType tradeType) {
        TradeMasterEntity entity = TradeMasterEntity.valueOf(jsonNode);

        entity.setTradeType(tradeType);
        entity.getLocation().setSigungu(cityCodeEntity.getName());
        entity.getLocation().setUmdCode(
                cityCodeRepository.findByRegionAndSigunguAndName(
                        cityCodeEntity.getRegion(),
                        cityCodeEntity.getSigungu(),
                        entity.getLocation().getDong()
                ).orElseGet(() -> cityCodeRepository.findByRegionAndSigunguAndFullnameLike(
                        cityCodeEntity.getRegion(),
                        cityCodeEntity.getSigungu(),
                        "%" + entity.getLocation().getDong() + "%"
                ).orElseGet(() -> {
                    log.error("findByRegionAndSigunguAndNameLike is null ==> {} / {} / {}", cityCodeEntity.getRegion(), cityCodeEntity.getSigungu(), entity.getLocation().getDong());
                    return cityCodeEntity;
                })).getUmd());
        entity.setCoordinate(coordinateService.searchCoordinate(entity).asEntity());
        if (entity.getDeal().getDealYear() == 0 || entity.getDeal().getDealMonth() == 0) {
            entity.setDeal(Deal.of(Integer.parseInt(dealYmd.substring(0, 3)), Integer.parseInt(dealYmd.substring(4, 5))));
        }

        return entity;
    }
}
