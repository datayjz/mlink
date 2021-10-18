package com.mlink.api.eventtime;

import com.mlink.api.eventtime.assignor.RecordTimestampAssignor;
import com.mlink.api.eventtime.assignor.TimestampAssigner;
import com.mlink.api.eventtime.assignor.TimestampAssignerSupplier;
import com.mlink.api.eventtime.generator.AscendingTimestampWatermarkGenerator;
import com.mlink.api.eventtime.generator.BoundedOutOfOrdernessWatermarksGenerator;
import com.mlink.api.eventtime.generator.NoWatermarksGenerator;
import com.mlink.api.eventtime.generator.WatermarkGenerator;
import com.mlink.api.eventtime.generator.WatermarkGeneratorSupplier;
import java.time.Duration;

/**
 * Watermark策略接口，可以自己实现策略(实现createWatermarkGenerator和createTimestampAssignor方法)，也可以使用
 * 公用的Watermark生成器去绑定自定义的Timestamp Assignor。
 *
 * 共用的Watermark生成器有以下4个(都是静态方法可以直接调用)：
 * 1. forBoundedOutOfOrderness，用于无序记录的Watermark，可以指定无序上界，生成器为BoundedOutOfOrdernessWatermarks。
 * 2. forMonotonousTimestamps， 为maxOutOfOrderness为0的forBoundedOutOfOrderness
 * 3. forGenerator，自定义WatermarkGenerator。
 * 4. noGenerator，不发送Watermark的Generator。
 *
 *
 * @param <T>
 */
public interface WatermarkStrategy<T>
    extends TimestampAssignerSupplier<T>, WatermarkGeneratorSupplier<T> {

    //--------------------------策略必须要实现的方法-----------------------------//
    @Override
    WatermarkGenerator<T> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context);

    @Override
    default TimestampAssigner<T> createTimestampAssigner(TimestampAssignerSupplier.Context context) {

        //对于记录来自带有时间戳的数据源，默认使用RecordTimestampAssignor，
        return new RecordTimestampAssignor<>();
    }

    //--------------------------通用策略-----------------------------//

    //--------------------------通用策略-----------------------------//

    /**
     * 指定Watermark生成器，Timestamp采用默认RecordTimestampAssignor
     */
    static <T> WatermarkStrategy<T> forGenerator(WatermarkGeneratorSupplier<T> generatorSupplier) {
        return generatorSupplier::createWatermarkGenerator;
    }

    static <T> WatermarkStrategy<T> forMonotonousTimestamp() {
        return (context) -> new AscendingTimestampWatermarkGenerator<>();
    }
    /**
     * 用于处理无序事件的watermark策略，可以为需要事件设置一个无序的上界。
     */
    static <T> WatermarkStrategy<T> forBoundOutOfOrderness(Duration maxOutOfOrderness) {
        return (context) -> new BoundedOutOfOrdernessWatermarksGenerator<>(maxOutOfOrderness);
    }

    /**
     * 一般用于processing time
     */
    static <T> WatermarkStrategy<T> noWatermarks() {
        //lambda实现创建WatermarkGenerator
        return (context) -> new NoWatermarksGenerator<>();
    }
}
