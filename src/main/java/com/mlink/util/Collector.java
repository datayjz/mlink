package com.mlink.util;

/**
 * 收集上游数据，并转发
 */
public interface Collector<T> {

    void collect(T record);

    void close();
}
