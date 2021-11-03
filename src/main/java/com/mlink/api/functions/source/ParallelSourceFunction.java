package com.mlink.api.functions.source;

/**
 * 并行执行source，由多个source实例来执行该Function。
 * 可使用RichParallelSourceFunction获取运行上下文信息。
 */
public interface ParallelSourceFunction<OUT> extends SourceFunction<OUT> {

}
