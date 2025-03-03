package com.payment.bo;

import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.bo.MockRuleBO;
import com.payment.simulator.server.bo.request.CacheRuleRequest;
import com.payment.simulator.server.bo.request.MockRuleRequest;
import com.payment.simulator.server.bo.response.CacheRuleResponse;
import com.payment.simulator.server.bo.response.MockRuleResponse;
import com.payment.simulator.server.entity.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @version 1.0
 * @description
 * @date 2022/04/11
 */
public class BoTest {

    @Test
    public void testAll() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        List<Class> list = new ArrayList<>();
        list.add(MockRuleRequest.class);
        list.add(MockRule.class);
        list.add(MockRuleBO.class);
        list.add(MockRuleResponse.class);
        list.add(CacheRuleRequest.class);
        list.add(CacheRuleQuery.class);
        list.add(CacheRule.class);
        list.add(CacheRuleBO.class);
        list.add(CacheRuleResponse.class);
        list.add(MockFeignRule.class);
        list.add(MockFeignRuleQuery.class);
//        list.add(MockContext.class);
//        list.add(MockContextThread.class);
        EntityVoTestUtils.justRun(list);
        Assert.assertTrue(true);
    }
}