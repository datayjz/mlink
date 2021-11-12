package com.mlink.state.backend;

import com.mlink.state.backend.StateBackend;

/**
 * 通过配置来获取状态存储后端的接口
 */
public interface ConfigurableStateBackend extends StateBackend {

    /**
     * 根据配置创建状态存储侯丹
     */
    StateBackend configure() throws IllegalStateException;
}
