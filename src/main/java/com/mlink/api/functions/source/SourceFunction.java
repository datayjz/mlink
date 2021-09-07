package com.mlink.api.functions.source;

import com.mlink.api.functions.Function;

/**
 * Flink中所有数据流数据源的基础接口，run方法用于开始发射数据，数据通过SourceContext来发射。
 * @param <T>
 */
public interface SourceFunction<T> extends Function {

    void run(SourceContext<T> ctx);

    void cancel();

    /**
     * 被SourceFunction的run方法调用，主要用于emit元素
     * @param <T>
     */
    interface SourceContext<T> {
        void collect(T element);

        void close();
    }
}
