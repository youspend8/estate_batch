package kr.co.estate;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class EstateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateApplication.class, args);
    }

}
