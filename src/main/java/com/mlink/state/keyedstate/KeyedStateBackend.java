package com.mlink.state.keyedstate;

import com.mlink.typeinfo.TypeSerializer;

/**
 * 用于管理keyed state
 */
public interface KeyedStateBackend<K> {

    void setCurrentKey(K newKey);

    K getCurrentKey();

    TypeSerializer<K> getKeySerializer();



}
