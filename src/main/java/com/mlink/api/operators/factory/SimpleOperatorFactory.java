package com.mlink.api.operators.factory;

import com.mlink.api.operators.StreamOperator;

public class SimpleOperatorFactory<OUT> extends  AbstractStreamOperatorFactory<OUT> {

    public static <OUT> SimpleOperatorFactory<OUT> of(StreamOperator<OUT> operator) {
        return null;
    }
}
