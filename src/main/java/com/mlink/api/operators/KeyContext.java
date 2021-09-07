package com.mlink.api.operators;

/**
 * 设置和查询当前keyed operator的key
 */
public interface KeyContext {

    void setCurrentKey(Object key);

    Object getCurrentKey();
}
