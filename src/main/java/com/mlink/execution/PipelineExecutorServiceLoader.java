package com.mlink.execution;

/**
 * 通过ServiceLoader的形式来获取执行Pipeline的PipelineExecutor。
 * 该ServiceLoader主要有：
 * DefaultExecutorServiceLoader：会根据service loader来查找对应的PipelineExecutorFactory
 * EmbeddedExecutorServiceLoader：专门使用EmbeddedExecutorFactory，一般用于main方法在cluster执行
 * WebSubmissionExecutorServiceLoader：专门使用WebSubmissionExecutorFactory
 * MiniClusterPipelineExecutorService：与MiniCluster关联
 */
public interface PipelineExecutorServiceLoader {

    /**
     * 根据配置来加载对应的PipelineExecutorFactor，比如是yarn的构建工厂、还是kubernetes的，还是local的
     */
    PipelineExecutorFactory getExecutorFactory() throws Exception;
}
