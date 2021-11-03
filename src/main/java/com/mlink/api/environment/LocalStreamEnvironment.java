package com.mlink.api.environment;


/**
 * 用于本地(多线程)执行Flink应用程序的StreamExecutionEnvironment，它会在后台生成一个嵌入式Flink集群，并在该集群执行应用程序。
 */
public class LocalStreamEnvironment extends StreamExecutionEnvironment {

    public LocalStreamEnvironment() {
    }


}
