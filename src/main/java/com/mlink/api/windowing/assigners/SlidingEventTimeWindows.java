package com.mlink.api.windowing.assigners;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.windowing.triggers.EventTimeTrigger;
import com.mlink.api.windowing.windows.TimeWindow;
import com.mlink.api.windowing.time.Time;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.typeinfo.TypeSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SlidingEventTimeWindows extends WindowAssigner<Object, TimeWindow> {

    private final long windowSize;

    private final long windowSlide;

    private final long startOffset;


    private SlidingEventTimeWindows(long windowSize, long windowSlide, long startOffset) {
        this.windowSize = windowSize;
        this.windowSlide = windowSlide;
        this.startOffset = startOffset;
    }

    public static SlidingEventTimeWindows of(Time windowSize, Time windowSlide) {
        return new SlidingEventTimeWindows(windowSize.toMillisecond(), windowSlide.toMillisecond(), 0);
    }

    public static SlidingEventTimeWindows of(Time windowSize, Time windowSlide, Time startOffset) {
        return new SlidingEventTimeWindows(windowSize.toMillisecond(), windowSlide.toMillisecond(),
            startOffset.toMillisecond());
    }


    @Override
    public Collection<TimeWindow> assignWindows(Object element,
                                                long timestamp,
                                                WindowAssignorContext context) {
        List<TimeWindow> windows = new ArrayList<>((int )(windowSize / windowSlide));
        long lastStart = TimeWindow.getWindowStartWithOffset(timestamp, startOffset, windowSlide);
        for (long start = lastStart; start > timestamp - windowSize; start -= windowSlide) {
            windows.add(new TimeWindow(start, start + windowSize));
        }
        return windows;
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
