package kr.co.estate.job;

import kr.co.estate.entity.TradeMasterEntity;
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
public class TradeJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TradeStepConfiguration tradeStepConfiguration;

    @Bean
    public Job tradeJob() {
        return jobBuilderFactory.get("tradeJob")
                .start(this.tradeStep())
                .build();
    }

    @Bean
    public Step tradeStep() {
        return stepBuilderFactory.get("tradeStep")
                .<List<TradeMasterEntity>, List<TradeMasterEntity>> chunk(1)
                .reader(tradeStepConfiguration)
                .writer(tradeStepConfiguration)
                .build();
    }
}
