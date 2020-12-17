package kr.co.estate.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.LocalDate;

@Getter
@ToString
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("json-files")
public class JsonFilesProperties {
    private final String basePath;
    private final String aggregation;

    public String getAggregationDirectory() {
        String path = getAggregationPath(0, "");
        return path.substring(0, path.lastIndexOf("/"));
    }

    public String getAggregationPath(int type, String tradeType) {
        return String.format("%s/%s/%s/%d_%s.json", basePath, aggregation, LocalDate.now(), type, tradeType);
    }
}
