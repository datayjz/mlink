package com.mlink.api.windowing.triggers;

import com.mlink.api.windowing.windows.Window;

/**
 * 接收其它触发器，当其它触发器触发fire，PurgingTrigger会触发fire，并清楚元素数据，也就是FIRE_AND_PURGE
 * @param <T>
 * @param <W>
 */
public class PurgingTrigger<T, W extends Window> extends Trigger<T, W>{

    private Trigger<T, W> nestedTrigger;

    private PurgingTrigger(Trigger<T, W> nestedTrigger) {
        this.nestedTrigger = nestedTrigger;
    }

    public static <T, W extends Window> PurgingTrigger of(Trigger<T, W> nestedTrigger) {
        return new PurgingTrigger<>(nestedTrigger);
    }

    @Override
    public TriggerResult onElement(T element, long timestamp, W window, TriggerContext context) {
        TriggerResult triggerResult = nestedTrigger.onElement(element, timestamp, window, context);
        return triggerResult.isFire() ? TriggerResult.FIRE_AND_PURGE : triggerResult;
    }

    @Override
    public TriggerResult onEventTime(long time, W window, TriggerContext context) throws Exception {
        TriggerResult triggerResult = nestedTrigger.onEventTime(time, window, context);
        return triggerResult.isFire() ? TriggerResult.FIRE_AND_PURGE : triggerResult;
    }

    @Override
    public TriggerResult onProcessingTime(long time, W window, TriggerContext context)
        throws Exception {
        TriggerResult triggerResult = nestedTrigger.onProcessingTime(time, window, context);
        return triggerResult.isFire() ? TriggerResult.FIRE_AND_PURGE : triggerResult;
    }

    @Override
    public boolean canMerge() {
        return nestedTrigger.canMerge();
    }

    @Override
    public void onMerge(W window, OnMergeContext context) throws Exception {
        nestedTrigger.onMerge(window, context);
    }

    @Override
    public void clear(W window, TriggerContext context) throws Exception {
        nestedTrigger.clear(window, context);
    }
}
