package com.mlink.api.environment;

import com.mlink.core.configuration.Configuration;

public interface StreamExecutionEnvironmentFactory {

    StreamExecutionEnvironment createExecutionEnvironment(Configuration configuration);
}
