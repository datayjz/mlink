package com.mlink.api.windowing.windows;

/**
 * 窗口所属的类型，是时间窗口还是global 窗口
 */
public abstract class Window {

    public abstract long maxTimestamp();
}
