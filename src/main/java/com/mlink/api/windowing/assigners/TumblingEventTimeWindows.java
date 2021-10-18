package com.mlink.api.windowing.assigners;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.windowing.triggers.EventTimeTrigger;
import com.mlink.api.windowing.windows.TimeWindow;
import com.mlink.api.windowing.time.Time;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.typeinfo.TypeSerializer;
import java.util.Collection;
import java.util.Collections;

/**
 * 基于event time的滚动窗口，窗口间没有overlap
 */
public class TumblingEventTimeWindows extends WindowAssigner<Object, TimeWindow> {

    //window time size
    private final long windowSize;
    //窗口偏移时间
    private final long startOffset;

    //窗口间gap策略，对齐、随机gap、下一窗口首到元素时间
    private final WindowStagger windowStagger;
    private Long staggerOffset = null;

    private TumblingEventTimeWindows(long windowSize,
                                     long startOffset,
                                     WindowStagger windowStagger) {
        this.windowSize = windowSize;
        this.startOffset = startOffset;
        this.windowStagger = windowStagger;
    }

    public static TumblingEventTimeWindows of(Time size) {
        return new TumblingEventTimeWindows(size.toMillisecond(), 0, WindowStagger.ALIGNED);
    }

    public static TumblingEventTimeWindows of(Time size, Time offset) {
        return new TumblingEventTimeWindows(size.toMillisecond(), offset.toMillisecond(),
            WindowStagger.ALIGNED);
    }

    public static TumblingEventTimeWindows of(Time size, Time offset, WindowStagger windowStagger) {
        return new TumblingEventTimeWindows(size.toMillisecond(), offset.toMillisecond(),
            windowStagger);
    }

    @Override
    public Collection<TimeWindow> assignWindows(Object element,
                                                long timestamp,
                                                WindowAssignorContext context) {

        if (staggerOffset == null) {
            staggerOffset = windowStagger.getStaggerOffset(context.getCurrentProcessingTime()
                , windowSize);
        }
        //定义的偏移时间+窗口错开时间(默认ALIGNED，也就是窗口间是对齐的), 第二个参数为起始偏移时间
        long start = TimeWindow.getWindowStartWithOffset(timestamp,
            (startOffset + staggerOffset) % windowSize, windowSize);
        return Collections.singletonList(new TimeWindow(start, start + startOffset));
    }

    @Override
    public Trigger getDefaultTrigger(StreamExecutionEnvironment environment) {
        return EventTimeTrigger.create();
    }

    @Override
    public TypeSerializer<TimeWindow> getWindowSerializer() {
        return null;
    }

    @Override
    public boolean isEventTime() {
        return true;
    }
}
