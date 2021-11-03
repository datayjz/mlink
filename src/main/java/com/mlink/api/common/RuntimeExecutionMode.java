package com.mlink.api.common;

public enum RuntimeExecutionMode {

    /**
     * Pipeline使用Streaming语义执行，所有task都会在开始执行前部署，开启Checkpoint，并且支持processing和event time
     */
    STREAMING,

    /**
     * Pipelines使用Batch语义执行，Task根据所属调度区域来启动，shuffler区域将会被阻塞，watermark被认为是完美的，也就是没有延迟数据。
     */
    BATCH,

    /**
     * 由source决定，如果所有source都是有界(bounded)，则会设置为BATCh。如果至少有一个source为无界(unbounded)，则设置为STREAMING
     */
    AUTOMATIC
}
