package com.payment.simulator.server.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @author Danny
 * @version v1.0
 * @date 2022.04.07
 **/
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Resource
    private MockRequestIntercept mockRequestIntercept;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mockRequestIntercept)
                .addPathPatterns("/**")// 拦截所有请求
                .excludePathPatterns("/api/v1/channel/**")//mock渠道内部的增删改查接口过滤掉
                .excludePathPatterns("/mock/feign/**")///mock/feign过滤掉
                .excludePathPatterns("/swagger**");///mock/feign过滤掉
        super.addInterceptors(registry);
    }
}
