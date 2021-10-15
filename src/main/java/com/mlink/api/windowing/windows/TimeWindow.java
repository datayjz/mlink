package com.mlink.api.windowing.windows;

import com.mlink.api.windowing.assigners.MergingWindowAssigner;
import com.mlink.util.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 指定时间间隔窗口，注意包括start，但是不包括end
 */
public class TimeWindow extends Window {

    private final long start;
    private final long end;

    public TimeWindow(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    /**
     * 判断两窗口是否相交
     */
    public boolean intersects(TimeWindow other) {
        return this.start<= other.end && this.end >= other.start;
    }

    /**
     * 计算涵盖两窗口的最小窗口
     */
    public TimeWindow cover(TimeWindow other) {
        return new TimeWindow(Math.min(this.start, other.start), Math.max(this.end, other.end));
    }

    //可以看到，范围不包括end
    @Override
    public long maxTimestamp() {
        return end - 1;
    }

    /**
     * 窗口合并，session window、global window等都是基于合并窗口来实现的
     */
    public static void mergeWindows(Collection<TimeWindow> windows,
                                    MergingWindowAssigner.MergeCallback<TimeWindow> mergeCallback) {

        List<TimeWindow> sortedWindows = new ArrayList<>(windows);
        //窗口集合由小到大排序
        Collections.sort(sortedWindows,  new Comparator<TimeWindow>() {
            @Override
            public int compare(TimeWindow o1, TimeWindow o2) {
                return Long.compare(o1.getStart(), o2.getStart());
            }
        });

        List<Tuple2<TimeWindow, Set<TimeWindow>>> merged = new ArrayList<>();
        Tuple2<TimeWindow, Set<TimeWindow>> currentMerge = null;
        for (TimeWindow candidate : sortedWindows) {
            //如果当前待merge窗口为null，则把当前遍历的窗口作为初始当前窗口
            if (currentMerge == null) {
                currentMerge = new Tuple2<>();
                currentMerge.f0 = candidate;
                currentMerge.f1 = new HashSet<>();
                currentMerge.f1.add(candidate);
            } else if (currentMerge.f0.intersects(candidate)) {
                //如果当前遍历的窗口包括该窗口，则将该窗口merge到当前窗口

                //合并当前窗口范围，能够囊括当前两个窗口
                currentMerge.f0 = currentMerge.f0.cover(candidate);
                currentMerge.f1.add(candidate);
            } else {
                //当前遍历的窗口和之前窗口不想交，则分离。
                merged.add(currentMerge);
                currentMerge = new Tuple2<>();
                currentMerge.f0 = candidate;
                currentMerge.f1 = new HashSet<>();
                currentMerge.f1.add(candidate);
            }
        }

        if (currentMerge != null) {
            merged.add(currentMerge);
        }

        for (Tuple2<TimeWindow, Set<TimeWindow>> t : merged) {
            if (t.f1.size() > 1) {
                mergeCallback.merge(t.f1, t.f0);
            }
        }
    }

    //获取传递时间timestamp所对应的窗口
    public static long getWindowStartWithOffset(long timestamp,
                                                long StartOffset,
                                                long windowSize) {

        return timestamp - (timestamp - StartOffset + windowSize) % windowSize;
    }
}
