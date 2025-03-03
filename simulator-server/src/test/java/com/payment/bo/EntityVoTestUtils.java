package com.payment.bo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: payment-api
 * @description:
 * 
 * @create: 2022-02-23 19:43
 **/
public class EntityVoTestUtils {

    // 实体化数据
    private static final Map<String, Object> STATIC_MAP = new HashMap<String, Object>();

    // 忽略的函数方法method
    private static final String NO_NOTICE = "getClass,notify,notifyAll,wait,equals,hashCode,clone";

    // private static final List<Class> CLASS_LIST = new ArrayList<Class>();

    static {
        STATIC_MAP.put("java.lang.Long", 1L);
        STATIC_MAP.put("java.lang.String", "test");
        STATIC_MAP.put("java.lang.Integer", 1);
        STATIC_MAP.put("int", 1);
        STATIC_MAP.put("long", 1);
        STATIC_MAP.put("java.util.Date", new Date());
        STATIC_MAP.put("char", '1');
        STATIC_MAP.put("java.util.Map", new HashMap());
        STATIC_MAP.put("boolean", true);

        // CLASS_LIST.add(.class);
        // CLASS_LIST.add(.class);
    }


    /**
     * @param CLASS_LIST 类列表
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static void justRun(List<Class> CLASS_LIST)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Class temp : CLASS_LIST) {
            Object tempInstance = new Object();
            // 执行构造函数
            Constructor[] constructors = temp.getConstructors();
            for (Constructor constructor : constructors) {
                final Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == 0) {
                    tempInstance = constructor.newInstance();
                } else {
                    Object[] objects = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        objects[i] = STATIC_MAP.get(parameterTypes[i].getName());
                    }
                    tempInstance = constructor.newInstance(objects);
                }
            }

            // 执行函数方法
            Method[] methods = temp.getMethods();
            for (final Method method : methods) {
                if (NO_NOTICE.contains(method.getName())) {
                    break;
                }
                final Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 0) {
                    Object[] objects = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        objects[i] = STATIC_MAP.get(parameterTypes[i].getName());
                    }
                    method.invoke(tempInstance, objects);
                } else {
                    method.invoke(tempInstance);
                }
            }
            // 输出执行完的类名
            // System.out.println(temp.getName());
        }
    }
}
