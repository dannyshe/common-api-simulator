package com.payment.simulator.common.sensors.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.payment.simulator.common.sensors.SensorsConfiguration;

/**
 * @author minn
 * @description
 * @date 2022/3/16
 */
@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SensorsConfiguration.class)
@Documented
public @interface EnableSensors {
}
