package com.mlink.state.api;

/**
 * 所有"分区状态"(partitioned state)的父接口。该接口及其子接口只能应用于KeyedStream，key是系统自动提供的，
 * 所以对于使用该State的函数总是获取的是当前element key对应的value。之所以这样做，目的是能够保证流和分区状态的一致性。
 */
public interface State {

    /**
     * 清除当前key对应的value
     */
    void clear();
}
