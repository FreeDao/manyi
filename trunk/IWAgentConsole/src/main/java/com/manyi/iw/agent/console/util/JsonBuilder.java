package com.manyi.iw.agent.console.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 用户构建json的简化方法
 *
 * 
 * 
 */
public class JsonBuilder {

    private Map<String, Object> json = null;
    private static ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(getClass());

    public static JsonBuilder start() {
        JsonBuilder builder = new JsonBuilder();
        builder.json = new HashMap<String, Object>(0);
        return builder;
    }

    public static JsonBuilder start(String key, Object obj) {
        return start().put(key, obj);
    }

    public JsonBuilder put(String key, Object obj) {
        json.put(key, obj);
        return this;
    }

    public Map<String, Object> get() {
        return this.json;
    }

    public String getJson() {
        try {
            return mapper.writeValueAsString(this.json);
        }
        catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    private JsonBuilder() {
    }

    @Override
    public String toString() {
        return getJson();
    }

}
