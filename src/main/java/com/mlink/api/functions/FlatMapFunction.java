package com.mlink.api.functions;

import com.mlink.api.operators.output.Collector;

/**
 * flat map函数接口，接收一个元素、对元素进行转换，最后输出0个、1个或多个元素。比如做一些split操作。
 * @param <IN>
 * @param <OUT>
 */
public interface FlatMapFunction<IN, OUT> extends Function {

    /**
     * 接收一条元素，执行transform逻辑，然后将结果(0个、1个或多个)放到结果收集器中
     */
    void flatMap(IN element, Collector<OUT> out) throws Exception;
}
