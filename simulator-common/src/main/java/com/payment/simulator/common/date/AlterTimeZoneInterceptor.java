package com.payment.simulator.common.date;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * 描述信息
 *
 * @author nevin
 * @createTime 2021-12-15
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
public class AlterTimeZoneInterceptor implements Interceptor {
    //设置timezone只执行一次标识
    private static volatile long timeZoneCount = 0;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        Connection connection = (Connection) args[0];
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("SET time_zone ='+0:00'");
        } finally {
            statement.close();
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
