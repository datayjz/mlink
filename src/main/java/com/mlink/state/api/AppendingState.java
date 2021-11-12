package com.mlink.state.api;

/**
 * 可用于追加的元素的partition state
 */
public interface AppendingState<IN, OUT> extends State {

    /**
     * 返回当前key state对应的value，这个OUT一般是一个集合
     */
    OUT get() throws Exception;

    /**
     * 向当前state中追加新元素
     */
    void add(IN value) throws Exception;
}
