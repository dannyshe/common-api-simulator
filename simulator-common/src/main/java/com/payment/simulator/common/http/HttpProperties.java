package com.payment.simulator.common.http;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "payment.http")
public class HttpProperties {

    private int httpMaxIdle = 20;
    private int httpKeepAlive = 20;
    private int connectionTimeout = 30;
    private int readTimeout = 1000;
    private int writeTimeout = 1000;
}
