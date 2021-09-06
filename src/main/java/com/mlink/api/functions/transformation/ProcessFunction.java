package com.mlink.api.functions.transformation;


import com.mlink.api.TimeDomain;
import com.mlink.api.functions.RichFunction;
import com.mlink.util.Collector;

/**
 * 处理数据流中每个元素，可以有0个或多个输出。该函数实现，可以通过Context查询时间和设置计时器(onTimer)，当计时器结束
 * onTimer被调用，并且可以再次输出0个或多个元素。
 *
 * 该函数只有在KeyStream上使用该函数，才可以使用keyed state和onTimer。
 */
public abstract class ProcessFunction<IN, OUT> implements RichFunction {

    public abstract void processElement(IN value, Context ctx, Collector<OUT> out) throws Exception;

    public abstract void onTimer(long timestamp, OnTimerContext ctx, Collector<OUT> out) throws Exception;


    public abstract class Context {

        /**
         * 当前元素处理的时间戳或触发计时器时间戳
         */
        public abstract Long timestamp();

    }

    public abstract class OnTimerContext extends Context {
        public abstract TimeDomain timeDomain();
    }
}
