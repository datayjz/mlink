package com.mlink.api.operators;

import com.mlink.api.common.OutputTag;
import com.mlink.api.functions.KeySelector;
import com.mlink.api.functions.WindowFunction;
import com.mlink.api.functions.transformation.ReduceFunction;
import com.mlink.api.windowing.assigners.WindowAssigner;
import com.mlink.api.windowing.evictors.Evictor;
import com.mlink.api.windowing.time.Time;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.api.windowing.windows.Window;

public class WindowOperatorBuilder<IN, K, W extends Window> {

    private final WindowAssigner<? super IN, W> windowAssigner;

    private final KeySelector<IN, K> keySelector;

    private Trigger<? super IN, ? super W> trigger;

    private Evictor<? super IN, ? super W> evictor;

    private long allowedLateness = 0L;

    private OutputTag<IN> lateDataOutputTag;

    public WindowOperatorBuilder(WindowAssigner<? super IN, W> windowAssigner,
                                 Trigger<? super IN, ? super W> trigger,
                                 KeySelector<IN, K> keySelector) {
        this.windowAssigner = windowAssigner;
        this.trigger = trigger;
        this.keySelector = keySelector;
    }

    /**
     * 设置窗口触发器，触发窗口计算
     */
    public void trigger(Trigger<? super IN, ? super W> trigger) {
        this.trigger = trigger;
    }

    /**
     * 窗口元素驱逐器，在窗口触发后，窗口函数执行前。
     * 如果设置，则不能进行增量聚合，可能会影响性能。
     */
    public void evictor(Evictor<? super IN, ? super W> evictor) {
        this.evictor = evictor;
    }

    /**
     * 窗口所允许的延迟时间
     */
    public void allowedLateness(Time lateness) {
        this.allowedLateness = lateness.toMillisecond();
    }

    /**
     * 延迟数据设置旁路输出
     */
    public void sideOutputLateData(OutputTag<IN> outputTag) {
        this.lateDataOutputTag = outputTag;
    }

    public <OUT> WindowOperator<K, IN, ?, OUT, W> reduce(ReduceFunction<IN> reduceFunction,
                                                         WindowFunction<IN, OUT, K, W> function) {

    }
}
