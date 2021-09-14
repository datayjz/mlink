package com.mlink.api.eventtime.generator;

import com.mlink.api.eventtime.WatermarkOutput;

/**
 * Watermark生成器，可以根据event time生成，也可以固定间隔时间生成。
 * @param <T>
 */
public interface WatermarkGenerator<T> {

    /**
     * 每个event都会调用次方法，可以根据event time定义水印发送策略
     */
    void onEvent(T event, long eventTimestamp, WatermarkOutput output);

    /**
     * 定期调用该方法，可以收到调用后发送Watermark。
     * @param output
     */
    void onPeriodicEmit(WatermarkOutput output);
}
