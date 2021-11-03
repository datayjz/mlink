package com.mlink.api.operators.factory;

import com.mlink.api.operators.AbstractUdfStreamOperator;
import com.mlink.api.operators.StreamOperator;
import com.mlink.api.operators.StreamSink;
import com.mlink.api.operators.StreamSource;

/**
 * 简单StreamOperator工厂类，仅用于包装一个已有StreamOperator
 */
public class SimpleOperatorFactory<OUT> extends  AbstractStreamOperatorFactory<OUT> {

    private final StreamOperator<OUT> operator;

    protected SimpleOperatorFactory(StreamOperator<OUT> operator) {
        this.operator = operator;
    }

    public static <OUT> SimpleOperatorFactory<OUT> of(StreamOperator<OUT> operator) {
        if (operator instanceof StreamSource) {
            //TODO input format
            return new SimpleOperatorFactory<>(operator);
        } else if (operator instanceof StreamSink) {
            //TODO output format
            return new SimpleOperatorFactory<>(operator);
        } else if (operator instanceof AbstractUdfStreamOperator) {
            //TODo udf operator factory
            return new SimpleOperatorFactory<>(operator);
        } else {
            return new SimpleOperatorFactory<>(operator);
        }
    }

    @Override
    public <T extends StreamOperator<OUT>> T createStreamOperator() {
        return (T) operator;
    }
}
