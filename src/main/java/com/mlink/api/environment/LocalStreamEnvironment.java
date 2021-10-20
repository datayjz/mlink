package com.mlink.api.environment;

import com.mlink.core.configuration.Configuration;

/**
 * 用于本地(多线程)执行Flink应用程序的StreamExecutionEnvironment，它会在后台生成一个嵌入式Flink集群，并在该集群执行应用程序。
 */
public class LocalStreamEnvironment extends StreamExecutionEnvironment {

    public LocalStreamEnvironment() {
        this(new Configuration());
    }

    public LocalStreamEnvironment(Configuration configuration) {
        super(validateAndGetConfiguration(configuration));
    }

    //核心就是传递一个本地部署配置
    private static Configuration validateAndGetConfiguration(final Configuration configuration) {
        final Configuration effectiveConfiguration = new Configuration(configuration);
        effectiveConfiguration.set("DEPLOPY TARGET", "local");
        return effectiveConfiguration;
    }
}
