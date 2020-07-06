package kr.co.estate.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.net.URL;

@Component
@Slf4j
public class ClientRequest {
    public String getResponse(URL url) {
        try {
//            log.info(">> ClientReqeust execute to :: " + url.toString());
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(10000)
                    .setConnectTimeout(10000)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setMaxConnTotal(60)
                    .setMaxConnPerRoute(60)
                    .build();

            HttpGet get = new HttpGet(url.toString());

            CloseableHttpResponse response = httpClient.execute(get);

            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error(">> ClientRequest occured [" + e.getCause() + "] by URL :: " + url.toString());
            if (e instanceof SocketTimeoutException) {
                getResponse(url);
            }
        }
        return null;
    }
}
