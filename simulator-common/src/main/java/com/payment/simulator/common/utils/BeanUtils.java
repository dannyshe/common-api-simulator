package com.payment.simulator.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;

public class BeanUtils {
    public static final Logger ERR_LOG = LoggerFactory.getLogger("COMMON-ERROR");

    public BeanUtils() {
    }

    public static Object getTargetBean(final Object bean) {
        Object targetBean = bean;

        try {
            while(targetBean instanceof Advised) {
                Object target = ((Advised)targetBean).getTargetSource().getTarget();
                if (target == targetBean) {
                    break;
                }

                targetBean = target;
            }
        } catch (Exception var3) {
            ERR_LOG.error("获取target时发生异常.", var3);
        }

        return targetBean;
    }

    public static Method getImplMethod(final Object bean, final Method interfaceMethod) throws Throwable {
        Object orgBean = getTargetBean(bean);
        Type[] var3 = interfaceMethod.getGenericParameterTypes();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Type type = var3[var5];
            if (type instanceof TypeVariable) {
                Method[] var7 = orgBean.getClass().getMethods();
                int var8 = var7.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    Method m = var7[var9];
                    if (StringUtils.equals(interfaceMethod.getName(), m.getName()) && m.getParameterTypes().length == interfaceMethod.getParameterTypes().length && m.getAnnotations().length > 0) {
                        return m;
                    }
                }
            }
        }

        return orgBean.getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
    }

    public static Method getAnnotationedMethod(final MethodInvocation invocation, final Class<? extends Annotation> clz) throws Throwable {
        Method interfaceMethod = invocation.getMethod();
        Method implementMethod = getImplMethod(invocation.getThis(), interfaceMethod);
        if (implementMethod.isAnnotationPresent(clz)) {
            return implementMethod;
        } else {
            return interfaceMethod.isAnnotationPresent(clz) ? interfaceMethod : null;
        }
    }
//
//    public static Method getAnnotationedMethod(final ProceedingJoinPoint joinPoint, final Class<? extends Annotation> clz) throws Throwable {
//        Method interfaceMethod = ((MethodSignature)joinPoint.getSignature()).getMethod();
//        Method implementMethod = getImplMethod(joinPoint.getThis(), interfaceMethod);
//        if (implementMethod.isAnnotationPresent(clz)) {
//            return implementMethod;
//        } else {
//            return interfaceMethod.isAnnotationPresent(clz) ? interfaceMethod : null;
//        }
//    }

    public static <T> T cloneBean(final T bean) {
        try {
            return (T)org.apache.commons.beanutils.BeanUtils.cloneBean(bean);
        } catch (Exception var2) {
            return null;
        }
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
