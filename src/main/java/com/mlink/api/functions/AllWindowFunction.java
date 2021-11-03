package com.mlink.api.functions;

import com.mlink.api.functions.Function;
import com.mlink.api.windowing.windows.Window;
import com.mlink.util.Collector;

/**
 * 用于计算non-keyed窗口函数
 */
public interface AllWindowFunction<IN, OUT, W extends Window> extends Function {

    /**
     * 对窗口元素进行计算并输出一条或多条数据
     */
    void apply(W window, Iterable<IN> values, Collector<OUT> out) throws Exception;
}
