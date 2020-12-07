package kr.co.estate.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.constants.TradeType;
import kr.co.estate.entity.CityCodeDTO;
import kr.co.estate.entity.TradeMasterDTO;
import kr.co.estate.repository.TradeMasterRepository;
import kr.co.estate.service.TradeService;
import lombok.RequiredArgsConstructor;
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
public class TradeStepConfiguration implements ItemReader<List<TradeMasterDTO>>, ItemWriter<List<TradeMasterDTO>> {
    private final TradeMasterRepository tradeMasterRepository;
    private final ConcurrentLinkedQueue<CityCodeDTO> cityCodeQueue;
    private final ForkJoinPool forkJoinPool;
    private final TradeService tradeService;

    @Override
    public List<TradeMasterDTO> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        CityCodeDTO cityCodeDTO = cityCodeQueue.poll();

        if (cityCodeDTO == null) {
            return null;
        }

        System.out.println("cityCodeDTO :: " + cityCodeDTO);

        String[] period = {
//                  "202005"
//                , "202004"
//                , "202003"
//                , "202002"
//                , "202001"
                "201912"
        };

        List<Callable<List<TradeMasterDTO>>> callableList = new ArrayList<>();

        List<TradeType> tradeTypeList = Arrays.asList(TradeType.values());

        for (String dealYmd : period) {
            List<Callable<List<TradeMasterDTO>>> temp = tradeTypeList.stream()
                    .map(x -> this.callable(cityCodeDTO, dealYmd, x))
                    .collect(Collectors.toList());
            callableList.addAll(temp);
        }

        List<Future<List<TradeMasterDTO>>> futureList =
                forkJoinPool.invokeAll(callableList);

        List<TradeMasterDTO> returnList = new ArrayList<>();

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
    public void write(List<? extends List<TradeMasterDTO>> list) throws Exception {
        System.out.println(">> count :: " + list.get(0).size());
        tradeMasterRepository.saveAllBatch(list.get(0));
    }

    public Callable<List<TradeMasterDTO>> callable(CityCodeDTO cityCodeDTO, String dealYmd, TradeType tradeType) {
        return () -> {
            final String lawdCd = cityCodeDTO.getRegion() + cityCodeDTO.getSigungu();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper
                    .readTree(tradeService.fetchTradePriceByType(lawdCd, dealYmd, tradeType))
                    .findPath("item");

            return StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(TradeMasterDTO::valueOf)
                    .peek(x -> {
                        x.setTradeType(tradeType);
                        x.setSigungu(cityCodeDTO.getName());
                    })
                    .collect(Collectors.toList());
        };
    }
}
