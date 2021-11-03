package com.mlink.api.timer;

import com.mlink.runtime.tasks.ProcessingTimeService;

public class InternalTimerServiceImpl<K, N> implements InternalTimerService<N> {


    public InternalTimerServiceImpl() {

    }

    @Override
    public long currentProcessingTime() {
        return 0;
    }

    @Override
    public long currentWatermark() {
        return 0;
    }

    @Override
    public void registerProcessingTimeTimer(N namespace, long time) {

    }

    @Override
    public void deleteProcessingTimeTimer(N namespace, long time) {

    }

    @Override
    public void registerEventTimeTimer(N namespace, long time) {

    }

    @Override
    public void deleteEventTimeTimer(N namespace, long time) {

    }
}
