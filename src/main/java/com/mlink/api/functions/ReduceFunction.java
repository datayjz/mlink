package com.mlink.api.functions;

public interface ReduceFunction<T> extends Function {

    T reduce(T value1, T value2) throws Exception;
}
