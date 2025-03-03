package com.payment.util;

import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.util.JSONUtil;
import com.payment.simulator.server.util.RequestPathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

/**
 * 工具类ut
 *
 * 
 * @date 2016-09-13
 */

@RunWith(SpringRunner.class)
public class UtilTest {

    @Test
    public void testAll() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        JSONObject jsonObject = RequestPathUtil.getPathParamFromUri("/abc/222/45", "/abc/{order}/{id}");
        Assert.assertEquals(jsonObject.get("order"), "222");
        String id = JSONUtil.getDataByPath(jsonObject, "id");
        Assert.assertEquals(id, "45");

    }

}