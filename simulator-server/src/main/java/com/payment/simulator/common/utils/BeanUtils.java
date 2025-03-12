package com.payment.simulator.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtils {
    public static final Logger ERR_LOG = LoggerFactory.getLogger("COMMON-ERROR");

    public BeanUtils() {
    }

    public static <T> T copyProperties(final Object source, Class<T> targetClazz, String... ignoreProperties) {
        try {
            T targetObject = targetClazz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, targetObject, ignoreProperties);
            return targetObject;
        } catch (Exception var4) {
            ERR_LOG.error("fail to copy properties", var4);
            return null;
        }
    }

    public static <T> List<T> copyListProperties(final List<?> sourceList, Class<T> targetClazz, String... ignoreProperties) {
        try {
            if (CollectionUtils.isEmpty(sourceList)) {
                return new ArrayList();
            } else {
                List<T> targetList = (List)sourceList.stream().map((source) -> {
                    return copyProperties(source, targetClazz, ignoreProperties);
                }).filter(Objects::nonNull).collect(Collectors.toList());

                assert targetList.size() == sourceList.size();

                return targetList;
            }
        } catch (Exception var4) {
            ERR_LOG.error("fail to copy list properties", var4);
            return new ArrayList();
        }
    }
}
