package com.mlink.api.windowing.assigners;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.windowing.triggers.EventTimeTrigger;
import com.mlink.api.windowing.windows.TimeWindow;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.typeinfo.TypeSerializer;
import java.util.Collection;
import java.util.Collections;

public class DynamicSessionEventTimeWindows<T> extends MergingWindowAssigner<T, TimeWindow> {

    private SessionTimeGapExtractor<T> extractor;

    private DynamicSessionEventTimeWindows(SessionTimeGapExtractor<T> extractor) {
        this.extractor = extractor;
    }

    public static <T> DynamicSessionEventTimeWindows<T> withDynamicGap(
        SessionTimeGapExtractor<T> extractor) {
        return new DynamicSessionEventTimeWindows<>(extractor);
    }

    @Override
    public void mergeWindow(Collection<TimeWindow> windows, MergeCallback<TimeWindow> callback) {
        TimeWindow.mergeWindows(windows, callback);
    }

    @Override
    public Collection<TimeWindow> assignWindows(T element,
                                                long timestamp,
                                                WindowAssignorContext context) {
        long sessionTimeout = extractor.extract(element);
        return Collections.singletonList(new TimeWindow(timestamp, timestamp + sessionTimeout));
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
