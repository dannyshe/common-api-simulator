package com.payment.simulator.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import io.vavr.jackson.datatype.VavrModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class ApiSimulatorServer {

    public static void main(String[] args) {
        log.info("application starting");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(ApiSimulatorServer.class, args);
    }

    @Bean
    public ObjectMapper responseCustomer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true).configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true).registerModule(new VavrModule(new VavrModule.Settings().useOptionInPlainFormat(true)));
        return objectMapper;
    }

    @Autowired
    public void configureJackson(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        jackson2ObjectMapperBuilder.modulesToInstall(VavrModule.class);
    }

//    @Bean
//    public ObjectMapper jacksonBuilder() {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.registerModule(new VavrModule());
//    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("application is up");
    }
}
