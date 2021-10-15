package com.mlink.api.windowing.assigners;

import com.mlink.api.windowing.windows.Window;
import java.util.Collection;

/**
 * 用于合并多个窗口，比如global window 和 session window都是基于merging window来实现的。
 */
public abstract class MergingWindowAssigner<T, W extends Window> extends WindowAssigner<T, W> {

    public abstract void mergeWindow(Collection<W> windows, MergeCallback<W> callback);

    public interface MergeCallback<W> {
        void merge(Collection<W> toMerged, W mergeResult);
    }
}
