package com.mlink.state;

/**
 * 专门用于聚合的state，基于AggregateFunction使用
 */
public interface AggregatingState<IN, OUT> extends MergingState<IN, OUT>  {

}
