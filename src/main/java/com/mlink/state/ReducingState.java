package com.mlink.state;

/**
 * 专门用于reducing state，基于reduce function
 */
public interface ReducingState<T> extends MergingState<T, T> {
}
