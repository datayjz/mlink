package com.mlink.api.functions.sink;

import com.mlink.api.functions.Function;

/**
 * 用户自定义sink function
 */
public interface SinkFunction<IN> extends Function {

    void invoke(IN value, Context context) throws Exception;

    /**
     * 用于获取输入输入数据的上下文信息
     */
    interface Context {

        long currentProcessingTime();

        long currentWatermark();

        long timestamp();
    }
}
