package com.payment.simulator.server.config;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableTTLConfig
public @interface EnablePayMockConfig {
}
