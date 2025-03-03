package com.payment.simulator.server.service;

import com.payment.simulator.server.annotation.Template;
import com.payment.simulator.server.template.MockTemplateInterface;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
@Component
public class TemplateService implements ApplicationContextAware {

    public static ConcurrentHashMap<String, MockTemplateInterface> templateMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, MockTemplateInterface> getTemplateMap() {
        return templateMap;
    }

    /**
     * 初始化渠道服务
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, MockTemplateInterface> beanMap = applicationContext.getBeansOfType(MockTemplateInterface.class);
        if (CollectionUtils.isEmpty(beanMap)) {
            log.info("[TemplateConfig.setApplicationContext] 不存在TemplateInterface的实现");
            return;
        }
        // 代理对象会使注解丢失,使用AnnotatedElementUtils
        for (MockTemplateInterface template : beanMap.values()) {
            boolean annotationPresent =
                    AnnotatedElementUtils.hasAnnotation(template.getClass(), Template.class);
            if (annotationPresent) {
                Template annotation =
                        AnnotatedElementUtils.findMergedAnnotation(template.getClass(), Template.class);
                templateMap.put(annotation.value(), template);
            }
        }
    }
}
