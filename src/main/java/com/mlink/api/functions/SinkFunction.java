package com.mlink.api.functions;

/**
 * 用户定义结果接收器的接口
 */
public interface SinkFunction<IN> extends Function {

    /**
     * 将一条结果写入到接收器中，每个record都会调用该函数。
     */
    void invoke(IN value, Context context) throws Exception;

    /**
     * Sink 函数想下文，能够用来获取输入数据的附加信息，比如timestamp、watermark
     */
    interface Context {

        long currentProcessingTime();

        long currentWatermark();

        Long timestamp();
    }
}
