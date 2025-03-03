package com.payment.simulator.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SiuPayTTLConfiguration.class)
@Documented
public @interface EnableTTLConfig {
}
