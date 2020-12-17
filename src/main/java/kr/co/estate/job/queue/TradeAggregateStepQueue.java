package kr.co.estate.job.queue;

import lombok.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
@ToString
public class TradeAggregateStepQueue {
    private LinkedList<QueueItem> queue;

    private TradeAggregateStepQueue(List<Integer> cityTypeList, List<String> tradeTypeList) {
        queue = new LinkedList<>();

        for (String tradeType : tradeTypeList) {
            for (int cityType : cityTypeList) {
                queue.add(QueueItem.builder()
                        .cityType(cityType)
                        .tradeType(tradeType)
                        .build());
            }
        }
    }

    /**
     * Static Factory Method
     * @return {@link TradeAggregateStepQueue}
     */
    public static LinkedList<QueueItem> getQueue() {
        return new TradeAggregateStepQueue(
                Arrays.asList(0, 1, 2, 3),
                Arrays.asList("TRADE", "RENT")).queue;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QueueItem {
        /**
         * 지역 코드 Type(0, 1, 2, 3)
         */
        private int cityType;

        /**
         * 실거래 타입 (매매 : TRADE, 전월세 : RENT)
         */
        private String tradeType;

        public List<Integer> getTradeTypeList() {
            switch (tradeType) {
                case "TRADE":
                    return Arrays.asList(0, 2);
                case "RENT":
                    return Arrays.asList(1, 3);
            }
            throw new IllegalArgumentException(tradeType + " : 올바르지 않은 값입니다.");
        }
    }
}
