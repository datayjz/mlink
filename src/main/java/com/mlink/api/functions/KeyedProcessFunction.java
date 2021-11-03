package com.mlink.api.functions;

import com.mlink.api.TimeDomain;
import com.mlink.api.timer.TimerService;
import com.mlink.api.common.OutputTag;
import com.mlink.util.Collector;

/**
 * 和ProcessFunction基本一致，唯一区别该Function是Keyed function，能够通过Context获取当前处理元素的key
 */
public abstract class KeyedProcessFunction<IN, KEY, OUT> implements RichFunction {

    public abstract void processElement(IN value, Context context, Collector<OUT> out);

    public abstract void onTimer(long timestamp, OntimerContext context, Collector<OUT> out);


    public abstract class Context {

        public abstract Long timestamp();

        public abstract TimerService timerServer();

        public abstract  <X> void output(OutputTag<X> outputTag, X value);

        public abstract KEY getCurrentKey();
    }

    public abstract class OntimerContext extends Context {
        public abstract TimeDomain timeDomain();

        public abstract KEY getCurrentKey();
    }
}
