package com.mlink.api.eventtime.generator;

import java.time.Duration;

/**
 * 延迟时间=周期间隔，所有数据event time都应该是单调递增的，不应该存在延迟
 */
public class AscendingTimestampWatermarkGenerator<T>
    extends BoundedOutOfOrdernessWatermarksGenerator<T>{

    public AscendingTimestampWatermarkGenerator() {
        super(Duration.ofMillis(0));
    }
}
