package com.mlink.api.operators;

import com.mlink.util.Collector;

/**
 * 为StreamOperator向下游发送数据元素和控制事件(比如barrier、watermark等)
 */
public interface Output<T> extends Collector<T> {


}
