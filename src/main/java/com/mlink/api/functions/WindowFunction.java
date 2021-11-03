package com.mlink.api.functions;

import com.mlink.api.windowing.windows.Window;
import com.mlink.util.Collector;

/**
 * 用于计算keyed window的基础接口
 */
public interface WindowFunction<IN, OUT, KEY, W extends Window> extends Function {

    /**
     * 对窗口元素进行计算并输出一个或多个元素
     */
    void apply(KEY key, W window, Iterable<IN> input, Collector<OUT> out) throws Exception;
}
