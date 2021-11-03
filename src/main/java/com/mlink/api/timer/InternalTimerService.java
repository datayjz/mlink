package com.mlink.api.timer;

/**
 * TimerService的内部版本，用于处理时间和计时器，可以在定时器的范围内指定key和namespace
 */
public interface InternalTimerService<N> {

    long currentProcessingTime();

    long currentWatermark();

    //传递的namespace将在timer触发时提供
    void registerProcessingTimeTimer(N namespace, long time);

    void deleteProcessingTimeTimer(N namespace, long time);

    void registerEventTimeTimer(N namespace, long time);

    void deleteEventTimeTimer(N namespace, long time);
}
