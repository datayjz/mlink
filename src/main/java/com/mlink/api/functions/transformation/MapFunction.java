package com.mlink.api.functions.transformation;

import com.mlink.api.functions.Function;

public interface MapFunction<IN, OUT> extends Function {

    OUT map(IN value) throws Exception;
}
