package com.mlink.api.functions;

import com.mlink.api.TimeDomain;
import com.mlink.api.timer.TimerService;
import com.mlink.api.common.OutputTag;
import com.mlink.util.Collector;

/**
 * Flink 中的Stateful stream processing。对于输入流中每个元素，都会调用processElement，并且可以输出0个或多个元素。
 * 如果ProcessFunction应用到KeyedStream，可以访问Keyed state和timer，timer用于注册计时器回调，回调方法也可以输出0个或多个元素。
 */
public abstract class ProcessFunction<IN, OUT> extends AbstractRichFunction {

    /**
     * input stream中每个元素调回调用该方法，可以通过Context修改内部state和设置触发器
     */
    public abstract void processElement(IN value, Context context, Collector<OUT> out) throws Exception;

    /**
     * 计时器回调方法
     */
    public void onTimer(long timestamp, OnTimerContext context, Collector<OUT> out) throws Exception {
    }


    public abstract class Context {

        /**
         * 当前处理元素的时间戳或者触发计时器的时间戳
         */
        public abstract Long timestamp();

        /**
         * 用于岔村时间和注册计时器的服务
         */
        public abstract TimerService timerServer();

        /**
         * 将记录发送到由output tag 标识的side output。这里相当于注册一个output，DataStream可以直接getOutput()
         */
        public abstract <X> void output(OutputTag<X> outputTag, X value);
    }

    public abstract class OnTimerContext extends Context {

        /**
         * 获取触发计时器
         */
        public abstract TimeDomain timeDomain();
    }
}
