package com.payment.simulator.common.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.util.ClassUtil;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.functors.ExceptionFactory;


public class MapUtils {

    /**
     * 根据keyMap 填充对象中field 与 keyMap.key 对应的 valueMap的值(返回新实体)
     *
     * @param tEntity
     * @param keyMap
     * @param valueMap
     * @param <T>
     * @return
     */
    public static <T> T transMappingEntity(Class<T> tEntity, Map<String, Object> keyMap, Map<String, Object> valueMap) {
        T entity = ClassUtil.createInstance(tEntity, true);
        if (null == keyMap || keyMap.isEmpty()) {
            return entity;
        }
        List<Field> fields = Arrays.asList(tEntity.getDeclaredFields());
        for (Entry<String, Object> entry : keyMap.entrySet()) {
            Field field = fields.stream().filter(x -> x.getName().equals(entry.getKey())).findFirst().orElse(null);
            if (null != field && null != entry.getValue()) {
                field.setAccessible(true);
                try {
                    field.set(entity, ConvertUtils.convert(valueMap.get(entry.getValue()), field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return entity;
    }

    /**
     * 根据keyMap 填充对象中field 与 keyMap.key 对应的 valueMap的值
     *
     * @param obj
     * @param keyMap
     * @param valueMap
     * @return
     */
    public static void transMappingFields(Object obj, Map<String, Object> keyMap, Map<String, Object> valueMap) {
        if (null == keyMap || null == valueMap || keyMap.isEmpty() || valueMap.isEmpty()) {
            return;
        }
        List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());
        for (Entry<String, Object> entry : keyMap.entrySet()) {
            Field field = fields.stream().filter(x -> x.getName().equals(entry.getKey())).findFirst().orElse(null);
            if (null != field && null != entry.getValue()) {
                field.setAccessible(true);
                try {
                    field.set(obj, ConvertUtils.convert(valueMap.get(entry.getValue()), field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    public static final char UNDERLINE = '_';
    public static final char SPACE = ' ';

    /**
     * map的key转换命名 Map Utils
     * 1.mapUtils
     * 2.map_utils
     *
     * @param map
     * @return map
     */
    public static Map<String, Object> replaceKey2Show(Map<String, Object> map) {
        Map<String, Object> reMap = new HashMap<>();
        if (reMap != null) {
            Iterator var2 = map.entrySet().iterator();

            while (var2.hasNext()) {
                Entry<String, Object> entry = (Entry) var2.next();
                if(entry.getKey().contains(String.valueOf(UNDERLINE))){
                    // map_utils ->> Map Utils
                    reMap.put(underlineToWord((String) entry.getKey()), map.get(entry.getKey()));
                } else {
                    // mapUtils ->> Map Utils
                    reMap.put(camelToWord((String) entry.getKey()), map.get(entry.getKey()));
                }
            }
            map.clear();
        }
        return reMap;
    }

    /**
     * 驼峰转单词
     * mapUtils ->> Map Utils
     *
     * @param param
     * @return
     */
    public static String camelToWord(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (i == 0) {
                sb.append(Character.toUpperCase(c));
                continue;
            }
            if(c >= 'A' && c <= 'Z') {
                // 将大写插入空格
                sb.append(SPACE);
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }


    /**
     * 下划线转单词 
     * map_utils ->> Map Utils
     *
     * @param param
     * @return
     */
    public static String underlineToWord(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (i == 0) {
                sb.append(Character.toUpperCase(c));
                continue;
            }
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(SPACE);
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }


    /**
     * 下划线转驼峰
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }


}
