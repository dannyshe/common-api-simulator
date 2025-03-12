package com.common.api.simulator.server.engine;

import com.common.api.simulator.server.dto.SimulateContext;
import com.common.api.simulator.server.constant.Constant;
import com.common.api.simulator.common.exception.ErrorCode;
import com.common.api.simulator.common.exception.SimulateException;
import lombok.extern.slf4j.Slf4j;

import javax.script.*;

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


    public static Boolean executeGroovyScript(SimulateContext context, String script){
        try {
            SCRIPT_ENGINE.eval(script,makeBindings(context));
            return (Boolean) ((Invocable) SCRIPT_ENGINE).invokeFunction(Constant.METHOD_NAME);
        } catch (ScriptException | NoSuchMethodException e) {
            log.info("executeGroovyScript script:{}",script);
            throw new SimulateException(ErrorCode.SERVER_ERROR,"执行脚本异常");
        }
    }

    public static String generateObjectId(SimulateContext context, String script){
        try {
            SCRIPT_ENGINE.eval(script,makeBindings(context));
            return (String) ((Invocable) SCRIPT_ENGINE).invokeFunction(Constant.METHOD_NAME);
        } catch (ScriptException | NoSuchMethodException e) {
            log.info("executeGroovyScript script:{}",script);
            throw new SimulateException(ErrorCode.SERVER_ERROR,"执行脚本异常:" + e.getMessage());
        }
    }

    private static Bindings makeBindings(SimulateContext context) {
        Bindings bindings = new SimpleBindings();
        bindings.put(ENV_CONTEXT, context);
        return bindings;
    }

}
