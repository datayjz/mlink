package com.mlink.api.windowing.evictors;

import com.mlink.api.windowing.windows.Window;
import java.util.Iterator;

public class CountEvictor<W extends Window> implements Evictor<Object, W> {

    private final long maxCount;
    private final boolean doEvictAfter;

    private CountEvictor(long count, boolean doEvictAfter) {
        this.maxCount = count;
        this.doEvictAfter = doEvictAfter;
    }

    public static <W extends Window> CountEvictor<W> of(long count) {
        return new CountEvictor<>(count, false);
    }

    public static <W extends Window> CountEvictor<W> of(long count, boolean doEvictAfter) {
        return new CountEvictor<>(count, doEvictAfter);
    }

    @Override
    public void evictBefore(Iterable<Object> elements, int size, W window,
                            EvictorContext evictorContext) {
        if (!doEvictAfter) {
            evict(elements, size);
        }
    }

    @Override
    public void evictAfter(Iterable<Object> elements, int size, W window,
                           EvictorContext evictorContext) {
        if (doEvictAfter) {
            evict(elements, size);
        }
    }

    //因为iterator是无序的，所以这里不保证移除删除的前后
    public void evict(Iterable<Object> elements, int size) {
        if (size <= maxCount) {
            return;
        } else {
            long evictCount = 0;
            long evictCutOff = size - maxCount;
            for (Iterator<Object> iterator = elements.iterator(); iterator.hasNext();) {
                iterator.next();
                evictCount++;
                if (evictCount > evictCutOff) {
                    break;
                } else {
                    iterator.remove();
                }
            }
        }
    }
}
