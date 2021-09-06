package com.mlink.api.functions.transformation;

import com.mlink.api.functions.Function;

public interface ReduceFunction<T> extends Function {

    T reduce(T value1, T value2) throws Exception;
}
