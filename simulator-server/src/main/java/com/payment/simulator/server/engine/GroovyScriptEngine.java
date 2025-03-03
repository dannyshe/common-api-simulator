package com.payment.simulator.server.engine;

import com.payment.simulator.server.bo.MockContext;
import com.payment.simulator.server.constant.Constant;
import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;

import javax.script.*;

/**
 * 
 * @version 0.0.1
 * @date 2022/04/06
 */
@Slf4j
public  class GroovyScriptEngine {

    /**
     * 脚本中获取上下文的变量名
     */
    public static final String ENV_CONTEXT = "context";

    /**
     * 脚本语言类型
     */
    public static final String SCRIPT_NAME = "groovy";

    private static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();

    /**
     * 脚本引擎，用于解析、执行脚本
     */
    private static final javax.script.ScriptEngine SCRIPT_ENGINE=SCRIPT_ENGINE_MANAGER.getEngineByName(SCRIPT_NAME);


    public static Boolean executeGroovyScript(MockContext context, String script){
        try {
            SCRIPT_ENGINE.eval(script,makeBindings(context));
            return (Boolean) ((Invocable) SCRIPT_ENGINE).invokeFunction(Constant.METHOD_NAME);
        } catch (ScriptException | NoSuchMethodException e) {
            log.info("executeGroovyScript script:{}",script);
            throw new PaymentException(ErrorCode.SERVER_ERROR,"执行脚本异常");
        }
    }

    private static Bindings makeBindings(MockContext context) {
        Bindings bindings = new SimpleBindings();
        bindings.put(ENV_CONTEXT, context);
        return bindings;
    }

}
