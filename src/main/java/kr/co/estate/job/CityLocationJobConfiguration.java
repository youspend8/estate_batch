package kr.co.estate.job;

import kr.co.estate.dto.CoordinateDto;
import kr.co.estate.entity.CityCodeEntity;
import kr.co.estate.repository.CityCodeRepository;
import kr.co.estate.service.CoordinateService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CityLocationJobConfiguration implements
        ItemProcessor<CityCodeEntity, CityCodeEntity>, ItemWriter<CityCodeEntity> {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CityCodeRepository cityCodeRepository;
    private final CoordinateService coordinateService;
    private final EntityManagerFactory entityManagerFactory;

    private final static String NAME_PREFIX = "cityLocation";
    private final static int DEFAULT_CHUNK_SIZE = 100;

    @Bean(name = NAME_PREFIX + "Job")
    public Job cityLocationJob() {
        return jobBuilderFactory.get(NAME_PREFIX + "Job")
                .start(cityLocationStep())
                .build();
    }

    @Bean(name = NAME_PREFIX + "Step")
    public Step cityLocationStep() {
        return stepBuilderFactory.get(NAME_PREFIX + "Step")
                .<CityCodeEntity, CityCodeEntity>chunk(DEFAULT_CHUNK_SIZE)
                .reader(reader())
                .processor(this)
                .writer(this)
                .build();
    }

    public JpaPagingItemReader<CityCodeEntity> reader() {
        return new JpaPagingItemReaderBuilder<CityCodeEntity>()
                .name("cityCodeJpaPagingReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(DEFAULT_CHUNK_SIZE)
                .queryString("SELECT CC FROM CityCodeEntity CC")
                .build();
    }

    @Override
    public CityCodeEntity process(CityCodeEntity cityCodeEntity) throws Exception {
        CoordinateDto coordinateDto = coordinateService.searchCoordinate(cityCodeEntity.getFullname(), "", "");

        cityCodeEntity.setCoordinate(coordinateDto.asEntity());

        return cityCodeEntity;
    }

    @Override
    public void write(List<? extends CityCodeEntity> list) throws Exception {
        cityCodeRepository.saveAll(list);
    }
}
