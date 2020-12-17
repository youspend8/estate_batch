package kr.co.estate.job;

import kr.co.estate.dto.trade.TradeAggsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("prod")
public class TradeAggregateJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TradeAggregateStepConfiguration tradeAggregateStepConfiguration;

    @Bean
    public Job tradeAggregateJob() {
        return jobBuilderFactory.get("tradeAggregateJob")
                .start(this.tradeAggregateStep())
                .build();
    }

    @Bean
    public Step tradeAggregateStep() {
        return stepBuilderFactory.get("tradeAggregateStep")
                .<List<TradeAggsDto>, List<TradeAggsDto>> chunk(1)
                .reader(tradeAggregateStepConfiguration)
                .writer(tradeAggregateStepConfiguration)
                .build();
    }
}
