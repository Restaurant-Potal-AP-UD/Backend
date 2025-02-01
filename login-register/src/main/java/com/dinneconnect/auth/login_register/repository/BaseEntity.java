package com.dinneconnect.auth.login_register.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseEntity {
    private long code;
    private Map<String, Object> properties;

    public BaseEntity() {
        this.code = new Random().nextLong(1000000000000L);
        this.properties = new HashMap<>();
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public long getCode() {
        return code;
    }

    public Map<String, Object> toDict() {
        Map<String, Object> dict = new HashMap<>(properties);
        dict.put("code", code);
        return dict;
    }
}
