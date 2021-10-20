package com.mlink.api.environment;

/**
 * 用于将应用程序代码发送到远端Flink集群的StreamExecutionEnvironment。
 */
public class RemoteStreamEnvironment extends StreamExecutionEnvironment{

    /**
     * 创建RemoteStreamEnvironment 指定由host、port指定master(JobManager)节点
     */
    public RemoteStreamEnvironment(String host, int port, String... jarFiles) {

    }
}
