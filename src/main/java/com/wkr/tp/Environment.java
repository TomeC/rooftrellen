package com.wkr.tp;

import com.wkr.tp.object.RtObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wkr
 * @description
 * @date 2024/6/10
 */
public class Environment {
    private Map<String, RtObject> store = new HashMap<>();
    private Environment outer;

    public RtObject set(String name, RtObject val) {
        store.put(name, val);
        return val;
    }
    public RtObject get(String name) {
        RtObject rtObject = store.get(name);
        if (rtObject != null) {
            return rtObject;
        }
        if (outer != null) {
            return outer.get(name);
        }
        return null;
    }
    public static Environment newEnclosedEnv(Environment outer) {
        Environment env = new Environment();
        env.outer = outer;
        return env;
    }
}
