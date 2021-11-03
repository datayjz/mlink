package com.mlink.api.operators;

import java.io.Serializable;

/**
 * StreamOperator是所有stream operator的基础接口，定义了其生命周期。
 * 所有流算子都需要实现其子接口：OneInputStreamOperator和TwoInputStreamOperator来处理元素。
 *
 * 抽象类AbstractStreamOperator为声明周期和属性方法提供了默认实现
 */
public interface StreamOperator<OUT> extends Serializable {

    /**
     * 算子初始化方法，元素处理前被调用。
     */
    void open() throws Exception;

    /**
     * 所有数据都被OneInputStreamOperator或TwoInputStreamOperator处理后调用
     */
    void close() throws Exception;

    /**
     * 生命周期中最后一步被调用。无论之前步骤成功还是失败，该方法都会被调用，用来彻底释放资源。
     */
    void dispose();
}
