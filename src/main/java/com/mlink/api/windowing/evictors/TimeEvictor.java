package com.mlink.api.windowing.evictors;

import com.mlink.api.windowing.time.Time;
import com.mlink.api.windowing.windows.Window;
import java.util.Iterator;

/**
 * TimeEvictor用于将元素保留一段时间，早于current_time - keep_time的元素将被驱逐。
 * current_time为当前窗口所有元素的最大时间戳，keep_time为传递入参
 */
public class TimeEvictor<W extends Window> implements Evictor<Object, W> {

    private final long windowSize;
    private final boolean doEvictorAfter;

    public TimeEvictor(long windowSize) {
        this.windowSize = windowSize;
        this.doEvictorAfter = false;
    }

    public TimeEvictor(long windowSize, boolean doEvictorAfter) {
        this.windowSize = windowSize;
        this.doEvictorAfter = doEvictorAfter;
    }

    public static <W extends Window> TimeEvictor<W> of(Time windowSize) {
        return new TimeEvictor<>(windowSize.toMillisecond());
    }

    public static <W extends Window> TimeEvictor<W> of(Time windowSize, boolean doEvictorAfter) {
        return new TimeEvictor<>(windowSize.toMillisecond(), doEvictorAfter);
    }

    @Override
    public void evictBefore(Iterable<Object> elements,
                            int size,
                            W window,
                            EvictorContext evictorContext) {
        if (!doEvictorAfter) {
            evict(elements);
        }
    }

    @Override
    public void evictAfter(Iterable<Object> elements, int size, W window,
                           EvictorContext evictorContext) {
        if (doEvictorAfter) {
            evict(elements);
        }
    }

    public void evict(Iterable<Object> elements) {
        long currentTime = getMaxTimestamp(elements);
        long evictCutoff = currentTime - windowSize;
        for (Iterator<Object> iterator = elements.iterator(); iterator.hasNext();) {
            Object record = iterator.next();
            //如果小于evictCutoff，则remove 该record
        }

    }

    private long getMaxTimestamp(Iterable<Object> elements) {
        return 1L;
    }
}
