package com.mlink.api.windowing.assigners;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.windowing.windows.TimeWindow;
import com.mlink.api.windowing.time.Time;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.typeinfo.TypeSerializer;
import java.util.Collection;
import java.util.Collections;

public class SessionEventTimeWindows extends MergingWindowAssigner<Object, TimeWindow> {

    private long sessionTimeout;

    private SessionEventTimeWindows(long sessionTimeout) {
        if (sessionTimeout <= 0) {
            throw  new IllegalArgumentException();
        }
        this.sessionTimeout = sessionTimeout;
    }

    public static SessionEventTimeWindows withGap(Time sessionTimeout) {
        return new SessionEventTimeWindows(sessionTimeout.toMillisecond());
    }

    public static <T> DynamicSessionEventTimeWindows withDynamicGap(SessionTimeGapExtractor<T> extractor) {
        return DynamicSessionEventTimeWindows.withDynamicGap(extractor);
    }



    @Override
    public void mergeWindow(Collection<TimeWindow> windows, MergeCallback<TimeWindow> callback) {
        TimeWindow.mergeWindows(windows, callback);
    }

    @Override
    public Collection<TimeWindow> assignWindows(Object element,
                                                long timestamp,
                                                WindowAssignorContext context) {
        return Collections.singletonList(new TimeWindow(timestamp, timestamp + sessionTimeout));
    }

    @Override
    public Trigger getDefaultTrigger(StreamExecutionEnvironment environment) {
        return null;
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
