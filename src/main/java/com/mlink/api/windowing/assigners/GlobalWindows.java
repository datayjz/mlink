package com.mlink.api.windowing.assigners;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.windowing.windows.GlobalWindow;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.typeinfo.TypeSerializer;
import java.util.Collection;
import java.util.Collections;

public class GlobalWindows extends WindowAssigner<Object, GlobalWindow> {

    private GlobalWindows() {}

    public static GlobalWindows create() {
        return new GlobalWindows();
    }

    @Override
    public Collection<GlobalWindow> assignWindows(Object element,
                                                  long timestamp,
                                                  WindowAssignorContext context) {
        return Collections.singletonList(GlobalWindow.get());
    }

    @Override
    public Trigger getDefaultTrigger(StreamExecutionEnvironment environment) {
        return null;
    }

    @Override
    public TypeSerializer<GlobalWindow> getWindowSerializer() {
        return null;
    }

    @Override
    public boolean isEventTime() {
        return false;
    }
}
