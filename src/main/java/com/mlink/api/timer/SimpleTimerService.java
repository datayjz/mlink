package com.mlink.api.timer;

/**
 * TimerService实现，用于InternalTimerService
 */
public class SimpleTimerService implements TimerService {

    public SimpleTimerService

    @Override
    public long currentProcessingTime() {
        return 0;
    }

    @Override
    public long currentWatermark() {
        return 0;
    }

    @Override
    public void registerProcessingTimeTimer(long time) {

    }

    @Override
    public void registerEventTimeTimer(long time) {

    }

    @Override
    public void deleteProcessingTimeTimer(long time) {

    }

    @Override
    public void deleteEventTimeTimer(long time) {

    }
}
