package com.mlink.api.operators.output;

/**
 * 收集一个数据，并将其转发，该收集器是push类型的
 */
public interface Collector<T> {

    void collect(T record);

    void close();
}
