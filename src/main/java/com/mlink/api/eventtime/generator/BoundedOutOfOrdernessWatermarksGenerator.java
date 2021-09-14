package com.mlink.api.eventtime.generator;

import com.mlink.api.eventtime.Watermark;
import com.mlink.api.eventtime.WatermarkOutput;
import java.time.Duration;

public class BoundedOutOfOrdernessWatermarksGenerator<T> implements WatermarkGenerator<T> {

    private final long outOfOrderness;

    private long maxTimestamp;

    public BoundedOutOfOrdernessWatermarksGenerator(Duration maxOutOfOrderness) {
        this.outOfOrderness = maxOutOfOrderness.toMillis();
        //之所以 + outOfOrderness + 1是用于处理一直没有数据的情况
        this.maxTimestamp = Long.MIN_VALUE + outOfOrderness + 1;
    }

    @Override
    public void onEvent(T event, long eventTimestamp, WatermarkOutput output) {
        maxTimestamp = Math.max(maxTimestamp, eventTimestamp);
    }

    //周期发送水印，最大延迟时间为：周期间隔 + 无序界限
    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
        output.emitWatermark(new Watermark(maxTimestamp - outOfOrderness - 1));
    }
}
