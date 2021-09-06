package com.mlink.api.functions.transformation;

import com.mlink.api.functions.Function;

public interface JoinFunction<IN1, IN2, OUT> extends Function {

    OUT join(IN1 first, IN2 second) throws Exception;
}
