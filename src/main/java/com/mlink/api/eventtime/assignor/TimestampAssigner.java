package com.mlink.api.eventtime.assignor;

/**
 * event time抽取器
 * @param <T>
 */
public interface TimestampAssigner<T> {

    long NO_TIMESTAMP = Long.MIN_VALUE;

    //recordTimestamp是针对一些数据源已经将event time拿出来的数据源，比如kafka，直接使用该数据即可。
    long extractTimestamp(T element, long recordTimestamp);
}
