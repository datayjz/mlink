package com.mlink.api.windowing.triggers;

import com.mlink.api.windowing.windows.TimeWindow;

/**
 * 所有基于Event time窗口默认Trigger实现。触发策略为基于Watermark，如果Watermark超过窗口末尾，则触发计算。
 */
public class EventTimeTrigger extends Trigger<Object, TimeWindow> {

    private EventTimeTrigger() {}

    public static EventTimeTrigger create() {
        return new EventTimeTrigger();
    }

    @Override
    public TriggerResult onElement(Object element,
                                   long timestamp,
                                   TimeWindow window,
                                   TriggerContext context) {
        //如果当前水印已经超过了当前窗口，则立即触发窗口计算
        if (window.maxTimestamp() <= context.getCurrentWatermark()) {
            return TriggerResult.FIRE;
        } else {
            return TriggerResult.CONTINUE;
        }
    }

    //暂时没太懂这里逻辑
    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext context)
        throws Exception {
        return time == window.maxTimestamp() ? TriggerResult.FIRE : TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext context)
        throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public boolean canMerge() {
        return true;
    }

    @Override
    public void onMerge(TimeWindow window, OnMergeContext context) throws Exception {
        //如果当前水印没有超过窗口末尾，只需要把当前窗口结束时间注册进来(通过onEventTime触发)。如果大于了窗口末尾，则
        if (window.maxTimestamp() > context.getCurrentWatermark()) {
            context.registerEventTimeTimer(window.maxTimestamp());
        }
    }

    @Override
    public void clear(TimeWindow window, TriggerContext context) throws Exception {
        context.deleteEventTimeTimer(window.maxTimestamp());
    }
}
