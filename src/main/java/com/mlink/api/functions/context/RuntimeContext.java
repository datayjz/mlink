package com.mlink.api.functions.context;

/**
 * RuntimeContext是Function执行的上下文信息，每个Function的并行实例都有一个RuntimeContext。
 * RuntimeContext包含了一些静态上下文信息、state
 */
public interface RuntimeContext {

    /**
     * 算子任务并行度
     */
    int getNumberOfParallelSubtasks();

    /**
     * 该DAG job中最大算子的并行度
     */
    int getMaxNumberOfParallelSubtasks();

    /**
     * 当前子任务所在算子的并发度的索引ID，0~NumberOfParallelSubtasks-1
     */
    int getIndexOfThisSubtask();
}