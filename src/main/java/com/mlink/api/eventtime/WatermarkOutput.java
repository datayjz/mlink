package com.mlink.api.eventtime;

/**
 * 用于发射Watermark和标记output状态。
 */
public interface WatermarkOutput {

    /**
     * 发射Watermark，并且将状态标记为active
     */
    void emitWatermark(Watermark watermark);


    /**
     * 将次output标记为idle，意味着下游Operator不用等待该output输出Watermark
     */
    void markIdle();

    /**
     * 将次output 标记active，意味着下游Operator应该等待次output输出的Watermark。
     */
    void markActive();
}
