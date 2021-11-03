package com.mlink.api.operators;

public interface InternalTimer<K, N> {

    /**
     * 返回此计时器的时间戳
     */
    long getTimestamp();

    /**
     * 绑定此计时器的key
     */
    K getKey();

    /**
     * 绑定此计时器的命名空间
     */
    N getNamespace();
}
