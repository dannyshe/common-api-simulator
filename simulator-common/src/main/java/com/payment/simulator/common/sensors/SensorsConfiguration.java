package com.payment.simulator.common.sensors;

import com.payment.simulator.common.sensors.service.SensorsClient;
import com.payment.simulator.common.sensors.service.SensorsService;
import com.payment.simulator.common.sensors.service.impl.SensorsServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.consumer.BatchConsumer;
import com.sensorsdata.analytics.javasdk.consumer.Consumer;
import com.sensorsdata.analytics.javasdk.consumer.DebugConsumer;

@EnableConfigurationProperties(value = SensorsProperties.class)
public class SensorsConfiguration {

    /**
     * 神策推送bean
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public ISensorsAnalytics sensorsAnalytics(Consumer consumer) {
        return new SensorsAnalytics(consumer);
    }


    @Bean
    public Consumer consumer(SensorsProperties sensorsProperties) {
        if (sensorsProperties.isDebug()) {
            return new DebugConsumer(sensorsProperties.getServerUrl(), true);
        } else {
            return new BatchConsumer(sensorsProperties.getServerUrl(), sensorsProperties.getBulkSize(),
                    sensorsProperties.getMaxCacheSize(), sensorsProperties.isThrowException());
        }
    }

    @Bean
    public SensorsService initSensorsService(){
        return new SensorsServiceImpl();
    }

    @Bean
    public SensorsClient initSensorsClient(){
        return new SensorsClient();
    }

}
