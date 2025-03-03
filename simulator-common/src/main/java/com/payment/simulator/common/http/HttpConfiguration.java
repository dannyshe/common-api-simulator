package com.payment.simulator.common.http;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
@EnableConfigurationProperties(value = HttpProperties.class)
public class HttpConfiguration {


    /**
     * 基于OkHttp3配置RestTemplate
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate(HttpProperties httpProperties) {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient(httpProperties)));
    }

    public OkHttpClient okHttpClient(HttpProperties httpProperties) {
        ConnectionPool okHttpConnectionPool = new ConnectionPool(httpProperties.getHttpMaxIdle(), httpProperties.getHttpKeepAlive(),
                TimeUnit.SECONDS);

        return new OkHttpClient.Builder()
                .readTimeout(httpProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(httpProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .connectionPool(okHttpConnectionPool)
                .connectTimeout(httpProperties.getConnectionTimeout(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
    }
}
