package com.mlink.api.functions.transformation;

import com.mlink.api.functions.Function;
import com.mlink.util.Collector;

public interface FlatMapFunction<IN, OUT> extends Function {

    //相较map、filter，因为是将一条数据拆多条，所以需要一个收集器收集拆分的结果
    void flatMap(IN value, Collector<OUT> collector) throws Exception;
}
