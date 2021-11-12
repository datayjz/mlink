package com.mlink.state.api;

/**
 * 专门用于reducing state，基于reduce function
 */
public interface ReducingState<T> extends MergingState<T, T> {
}
