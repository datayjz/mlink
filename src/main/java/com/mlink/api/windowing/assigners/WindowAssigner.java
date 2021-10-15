package com.mlink.api.windowing.assigners;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.windowing.windows.Window;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.typeinfo.TypeSerializer;
import java.io.Serializable;
import java.util.Collection;

public abstract class WindowAssigner<T, W extends Window> implements Serializable {


    /**
     * 返回当前元素所分配到的窗口
     */
    public abstract Collection<W> assignWindows(T element,
                                                long timestamp,
                                                WindowAssignorContext context);

    public abstract Trigger getDefaultTrigger(StreamExecutionEnvironment environment);

    public abstract TypeSerializer<W> getWindowSerializer();

    public abstract boolean isEventTime();

    /**
     * 窗口分配器上下文，用于查询当提前处理时间
     */
    public abstract static class WindowAssignorContext {

        public abstract long getCurrentProcessingTime();
    }
}
