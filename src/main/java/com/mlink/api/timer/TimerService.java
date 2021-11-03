package com.mlink.api.timer;

/**
 * 用于处理时间和计时器的接口
 */
public interface TimerService {

    /**
     * 放回当前处理时间
     */
    long currentProcessingTime();

    /**
     * 返回当前event-time watermark
     */
    long currentWatermark();

    /**
     * 注册一个处理时间计时器
     */
    void registerProcessingTimeTimer(long time);

    /**
     * 注册一个事件时间计时器
     */
    void registerEventTimeTimer(long time);

    //只有已被注册，并且还没有到期才可以删除
    void deleteProcessingTimeTimer(long time);

    void deleteEventTimeTimer(long time);
}
