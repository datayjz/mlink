package com.mlink.api.windowing.evictors;

import com.mlink.api.windowing.windows.Window;

public interface Evictor <T, W extends Window>{

    /**
     * 窗口函数执行前调用
     */
    void evictBefore(Iterable<T> elements, int size, W window, EvictorContext evictorContext);

    /**
     * 窗口函数执行后调用
     * size：当前窗口内元素数量
     */
    void evictAfter(Iterable<T> elements, int size, W window, EvictorContext evictorContext);


    interface EvictorContext {

        long getCurrentProcessingTime();

        long getCurrentWatermark();
    }
}
