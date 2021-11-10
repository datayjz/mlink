package com.mlink.state;

/**
 * 标识两个MergingState类型的state可以合并为一个state实例
 */
public interface MergingState<IN, OUT> extends AppendingState<IN, OUT> {
}
