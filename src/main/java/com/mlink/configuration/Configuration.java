package com.mlink.configuration;

import java.util.HashMap;

/**
 * 用于存储key/value的轻量级配置
 */
public class Configuration {

    protected final HashMap<String, Object> confData;

    public Configuration() {
        this.confData = new HashMap<>();
    }


}
