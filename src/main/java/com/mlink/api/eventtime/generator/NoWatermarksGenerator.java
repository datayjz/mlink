package com.mlink.api.eventtime.generator;

import com.mlink.api.eventtime.WatermarkOutput;

/**
 * 不发送Watermark实现，一般用于processing time数据流。
 * @param <T>
 */
public class NoWatermarksGenerator<T> implements WatermarkGenerator<T> {

    //空实现
    @Override
    public void onEvent(T event, long eventTimestamp, WatermarkOutput output) {
    }

    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
    }
}
