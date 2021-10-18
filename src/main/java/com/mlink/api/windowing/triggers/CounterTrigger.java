package com.mlink.api.windowing.triggers;

import com.mlink.api.windowing.windows.Window;

/**
 * 计数触发器，当窗口元素到达指定个数后，触发窗口函数计算
 * @param <W>
 */
public class CounterTrigger<W extends Window> extends Trigger<Object, W>{

    private final long maxCount;
    private long count = 0;

    private CounterTrigger(long maxCount) {
        this.maxCount = maxCount;
    }

    public static <W extends Window> CounterTrigger<W> of(long maxCount) {
        return new CounterTrigger<>(maxCount);
    }

    @Override
    public TriggerResult onElement(Object element,
                                   long timestamp,
                                   W window,
                                   TriggerContext context) {
        //TODO 有state后借助state
        count++;
        if (count >= maxCount) {
            count = 0;
            return TriggerResult.FIRE;
        }
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onEventTime(long time, W window, TriggerContext context) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onProcessingTime(long time, W window, TriggerContext context)
        throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public void clear(W window, TriggerContext context) throws Exception {

    }
}
