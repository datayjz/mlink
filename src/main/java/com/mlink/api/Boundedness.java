package com.mlink.api;

/**
 * 用以表示流是否有界，分为有界流(BOUNDED)和无界流(UNBOUNDED)
 */
public enum Boundedness {

    /**
     * 具有有限记录的有界数据流
     */
    BOUNDED,

    /**
     * 具有无限记录的无限数据流
     */
    CONTINUOUS_UNBOUNDED
}
