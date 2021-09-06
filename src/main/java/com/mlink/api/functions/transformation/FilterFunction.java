package com.mlink.api.functions.transformation;

import com.mlink.api.functions.Function;

public interface FilterFunction<T> extends Function {

    boolean filter(T value) throws Exception;
}
