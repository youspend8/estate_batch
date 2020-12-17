package kr.co.estate;

import kr.co.estate.config.properties.JsonFilesProperties;
import kr.co.estate.config.properties.KakaoApiProperties;
import kr.co.estate.config.properties.NaverApiProperties;
import kr.co.estate.config.properties.OpenApiProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableBatchProcessing
@EnableConfigurationProperties(value = {
        OpenApiProperties.class,
        KakaoApiProperties.class,
        NaverApiProperties.class,
        JsonFilesProperties.class
})
@MapperScan(basePackages = "kr.co.estate.mapper")
public class EstateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateApplication.class, args);
    }

}
