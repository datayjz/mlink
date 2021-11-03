package com.mlink.api.common;

/**
 * 该配置定义了应用程序执行行为，主要包括以下配置：
 * 1. 应用程序默认并发度，如果算子没有指定并发，则会使用该默认并发
 */
public class ExecutionConfig {

    private int parallelism = 1;

    private int maxParallelism = -1;



    public ExecutionConfig setParallelism(int parallelism) {
        this.parallelism = parallelism;
        return this;
    }

    public int getParallelism() {
        return parallelism;
    }

    public ExecutionConfig setMaxParallelism(int maxParallelism) {
        this.maxParallelism = maxParallelism;
        return this;
    }

    public int getMaxParallelism() {
        return maxParallelism;
    }
}
