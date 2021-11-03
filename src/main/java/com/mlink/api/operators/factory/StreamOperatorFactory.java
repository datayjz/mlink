package com.mlink.api.operators.factory;

import com.mlink.api.operators.ChainingStrategy;

/**
 * 用于创建StreamOperator的工厂接口，不同类型的Operator，使用各自工厂
 * @param <OUT>
 */
public interface StreamOperatorFactory<OUT> {

    void setChainingStrategy(ChainingStrategy strategy);

    default boolean isStreamSource() {
        return false;
    }
}
