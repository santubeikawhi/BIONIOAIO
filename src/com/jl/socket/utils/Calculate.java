package com.jl.socket.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @ClassName Calculate
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/25 23:27
 * @Version 1.0
 */
public final class Calculate {
    private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    public static Object cal(String expression) throws ScriptException {
        return jse.eval(expression);
    }
}
