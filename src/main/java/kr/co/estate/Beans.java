package kr.co.estate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.estate.common.TradeType;
import kr.co.estate.entity.CityCodeDTO;
import kr.co.estate.entity.TradeMasterDTO;
import kr.co.estate.repository.CityCodeRepository;
import kr.co.estate.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
@RequiredArgsConstructor
public class Beans {
    private final TradeService tradeService;
    private final CityCodeRepository cityCodeRepository;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Bean(name="tradeMasterQueue")
    public ArrayDeque<TradeMasterDTO> tradeMasterQueue() throws JsonProcessingException {
        return this.init();
    }

    public ArrayDeque<TradeMasterDTO> init() {
        ArrayDeque<TradeMasterDTO> queue = new ArrayDeque<>();

        List<CityCodeDTO> cityCodeList = cityCodeRepository.findAllByType("1");

//        String[] period = {"202004", "202003", "202002", "202001"};

//        for (String dealYmd : period) {
//            List<Callable<List<ApartmentTradePriceDTO>>> temp = cityCodeList.stream()
//                    .map(x -> this.callable(x, dealYmd))
//                    .collect(Collectors.toList());
//            callableList.addAll(temp);
//        }

        List<Callable<List<TradeMasterDTO>>> callableList = new ArrayList<>();

        List<TradeType> tradeTypeList = Arrays.asList(TradeType.values());

        String[] period = {"202005"
//                , "202004", "202003", "202002", "202001"
        };

        for (String dealYmd : period) {
            for (CityCodeDTO cityCodeDTO : cityCodeList) {
                List<Callable<List<TradeMasterDTO>>> temp = tradeTypeList.stream()
                        .map(x -> this.callable(cityCodeDTO, dealYmd, x))
                        .collect(Collectors.toList());
                callableList.addAll(temp);
            }
        }

        List<Future<List<TradeMasterDTO>>> futureList =
                forkJoinPool.invokeAll(callableList);

        futureList.forEach(x -> {
            if (x.isDone()) {
                try {
                    queue.addAll(x.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        return queue;
    }

    public Callable<List<TradeMasterDTO>> callable(CityCodeDTO cityCodeDTO, String dealYmd, TradeType tradeType) {
        return () -> {
            final String lawdCd = cityCodeDTO.getRegion() + cityCodeDTO.getSigungu();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper
                    .readTree(tradeService.fetchTradePriceByType(lawdCd, dealYmd, tradeType))
                    .findPath("item");

            System.out.println("jsonNode :: " + jsonNode);

            return StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(TradeMasterDTO::valueOf)
                    .peek(x -> {
                        x.setTradeType(tradeType);
                        x.setSigungu(cityCodeDTO.getName());
                    })
                    .collect(Collectors.toList());
        };
    }

    public Callable<List<TradeMasterDTO>> callable(String lawdCd, String dealYmd, TradeType tradeType) {
        return () -> {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper
                    .readTree(tradeService.fetchTradePriceByType(lawdCd, dealYmd, tradeType))
                    .findPath("item");

            System.out.println("jsonNode :: " + jsonNode);

            return StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(TradeMasterDTO::valueOf)
                    .peek(x -> x.setTradeType(tradeType))
                    .collect(Collectors.toList());
        };
    }
}
