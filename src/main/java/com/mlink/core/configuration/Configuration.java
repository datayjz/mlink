package com.mlink.core.configuration;

import com.mlink.api.common.ReableConfig;
import java.util.Map;

public class Configuration implements ReableConfig {
    private Map<String, Object> confData;

    public Configuration() {}

    public Configuration(Configuration otherConfiguration) {}

    public <T> Configuration set(String option, T value) {
        return this;
    }
}
