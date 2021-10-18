package com.mlink.api.windowing.triggers;

import com.mlink.api.windowing.windows.Window;
import java.io.Serializable;

/**
 * Trigger用于触发窗口对当前部分数据进行计算(将窗口数据应用到窗口Function中)，之所以说部分数据，是因为Trigger触发时
 * 可能当前窗口还没有完成，这次Trigger只会将当前窗口内部分数据计算输出。
 */
public abstract class Trigger<T, W extends Window> implements Serializable {

    public abstract TriggerResult onElement(T element, long timestamp, W window, TriggerContext context);

    /**
     * 当TriggerContext中的event time被注册是触发
     */
    public abstract TriggerResult onEventTime(long time, W window, TriggerContext context) throws Exception;

    /**
     * 当TriggerContext中的process time被注册时触发
     */
    public abstract TriggerResult onProcessingTime(long time, W window, TriggerContext context) throws Exception;

    /**
     * 当前触发器是否支持窗口merge(session window)
     */
    public boolean canMerge() {
        return false;
    }

    /**
     * 当窗口合并时被调用
     */
    public void onMerge(W window, OnMergeContext context) throws Exception {
        throw new UnsupportedOperationException();
    }

    public abstract void clear(W window, TriggerContext context) throws Exception;

    public interface TriggerContext {
        //获取当前处理时间
        long getCurrentProcessingTime();

        //获取当前水印
        long getCurrentWatermark();

        //注册一个事件时间回调，当指定时间的Watermark传递进来时，回调该方法。该方法再去触发onEventTime。
        void registerEventTimeTimer(long time);

        //注册一个系统时间回调，当传递指定系统时间时回调该方法，该方法在触发onProcessingTime方法。
        void registerProcessingTimeTimer(long time);

        /**
         * 删除指定时间的事件时间触发器
         */
        void deleteEventTimeTimer(long time);

        /**
         * 删除指定时间的处理时间触发器
         * @param time
         */
        void deleteProcessingTimeTimer(long time);

    }

    public interface OnMergeContext extends TriggerContext {
    }
}
