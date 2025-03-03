package com.payment.simulator.common.date;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class DynamicDateInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object object = invocation.getArgs()[1];
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            //插入操作时，自动插入env
            Field fieldCreate = object.getClass().getDeclaredField("created");
            fieldCreate.setAccessible(true);
            fieldCreate.set(object, formatTZ(new Date()));
        }else{
            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                //update时，自动更新updated_at
                Field fieldUpdate = object.getClass().getDeclaredField("updated");
                fieldUpdate.setAccessible(true);
                fieldUpdate.set(object, formatTZ(new Date()));
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
    public static Date formatTZ (Object time) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (time instanceof Date) {
            localDateTime = LocalDateTime.ofInstant(((Date)time).toInstant(), TimeZone.getTimeZone("UTC").toZoneId());
        } else if (time instanceof String) {
            localDateTime = LocalDateTime.parse((String)time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return DateUtils.local2Date(localDateTime);
    }
}
