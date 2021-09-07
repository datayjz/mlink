package com.mlink.api.operators;

import com.mlink.record.StreamRecord;

/**
 * 具有单个输入流的算子接口
 */
public interface OneInputStreamOperator<IN, OUT> extends StreamOperator<OUT> {

    /**
     * 处理到达的元素
     */
    void processElement(StreamRecord<IN> element) throws Exception;
}
