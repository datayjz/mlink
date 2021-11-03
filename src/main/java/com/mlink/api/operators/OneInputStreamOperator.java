package com.mlink.api.operators;

import com.mlink.runtime.streamrecord.StreamRecord;

/**
 * 具有单个输入流的算子接口。如果需要实现自定义operator，除了实现该接口(或TwoInputStreamOperator)
 * 还需要继承AbstractStreamOperator或AbstractUDFStreamOperator。
 */
public interface OneInputStreamOperator<IN, OUT> extends StreamOperator<OUT> {

    /**
     * 处理到达的元素
     */
    void processElement(StreamRecord<IN> element) throws Exception;
}
