package com.payment.simulator.server.config;

import com.payment.simulator.common.date.AlterTimeZoneInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;

@MapperScan("com.payment.simulator.server.mapper")
public class MybatisConfiguration {

    @Bean
    public AlterTimeZoneInterceptor alterTimeZoneInterceptor() {
        return new AlterTimeZoneInterceptor();
    }
}