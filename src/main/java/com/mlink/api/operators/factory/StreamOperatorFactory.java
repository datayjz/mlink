package com.mlink.api.operators.factory;

import com.mlink.api.operators.ChainingStrategy;
import com.mlink.api.operators.StreamOperator;

/**
 * 用于创建StreamOperator的工厂接口，不同类型的Operator使用各自工厂创建。
 * 主要用于在v2版本，目前
 */
public interface StreamOperatorFactory<OUT> {

    <T extends StreamOperator<OUT>> T createStreamOperator();

    void setChainingStrategy(ChainingStrategy strategy);

    ChainingStrategy getChainingStrategy();

    default boolean isStreamSource() {
        return false;
    }
}
