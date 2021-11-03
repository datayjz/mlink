package com.mlink.api.operators;

import com.mlink.api.common.OutputTag;
import com.mlink.record.StreamRecord;
import com.mlink.util.Collector;

/**
 * 为StreamOperator向下游发送数据元素和控制事件(比如barrier、watermark等)
 */
public interface Output<T> extends Collector<T> {


    <X> void collect(OutputTag<X> outputTag, StreamRecord<T> record);
}
