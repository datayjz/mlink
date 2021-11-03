package com.mlink.api.operators.output;

/**
 * 用于StreamOperator向下游发送数据元素和控制事件(比如barrier、watermark等)
 */
public interface Output<T> extends Collector<T> {

}
