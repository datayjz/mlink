package com.mlink.api.functions;

public interface AggregateFunction<IN, ACC, OUT> extends Function {

    ACC createAccumulator();

    ACC add(IN value, ACC accumulator);

    OUT getResult(ACC accumulator);

    ACC merge(ACC a, ACC b);
}
