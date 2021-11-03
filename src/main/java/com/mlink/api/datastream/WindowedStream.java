package com.mlink.api.datastream;

import com.mlink.api.common.OutputTag;
import com.mlink.api.functions.transformation.ReduceFunction;
import com.mlink.api.operators.WindowOperatorBuilder;
import com.mlink.api.windowing.assigners.WindowAssigner;
import com.mlink.api.windowing.evictors.Evictor;
import com.mlink.api.windowing.time.Time;
import com.mlink.api.windowing.triggers.Trigger;
import com.mlink.api.windowing.windows.Window;

/**
 * WindowedStream代表一组以key分组的数据流，对于每个元素通过WindowAssigner来划分窗口。
 * WindowedStream只是一个纯API操作，实际运行时，WindowedStream将会和KeyedStream算子运行在一起，内部合并为一个Operator
 */
public class WindowedStream<T, K, W extends Window> {

    private final KeyedStream<T, K> input;

    private final WindowOperatorBuilder<T, K, W> builder;

    public WindowedStream(KeyedStream<T, K> input, WindowAssigner<? super T, W > windowAssigner) {
        this.input = input;
        this.builder = new WindowOperatorBuilder<>(
            windowAssigner,
            windowAssigner.getDefaultTrigger(input.getExecutionEnvironment()),
            input.getKeySelector());
    }

    public WindowedStream<T, K, W> trigger(Trigger<? super T, ? super W> trigger) {
        builder.trigger(trigger);
        return this;
    }

    public WindowedStream<T, K, W> evictor(Evictor<? super T, ? super W> evictor) {
        builder.evictor(evictor);
        return this;
    }

    public WindowedStream<T, K, W> allowedLateness(Time lateness) {
        builder.allowedLateness(lateness);
        return this;
    }

    public WindowedStream<T, K, W> sideOutputLateData(OutputTag<T> outputTag) {
        builder.sideOutputLateData(outputTag);
        return this;
    }

    public SingleOutputStreamOperator<T> reduce(ReduceFunction<T> function) {
        return reduce(function, )
    }
}
