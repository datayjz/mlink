package com.mlink.state.api;

import java.io.IOException;

/**
 * 单值状态，每个key对应的state是一个单值(各种数据类型的)
 */
public interface ValueState<T> extends State {

    /**
     * 返回当前key对应的state value
     */
    T value() throws IOException;

    /**
     * 更新当前算子key对应的state value
     */
    void update(T value) throws IOException;
}
