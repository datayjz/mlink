package com.mlink.api.operators;

import com.mlink.record.StreamRecord;

/**
 * 具有两个输入流的算子接口
 */
public interface TwoInputStreamOperator<IN1, IN2, OUT> extends StreamOperator<OUT> {

    /**
     * 处理第一个元素
     */
    void processElement1(StreamRecord<IN1> element) throws Exception;

    /**
     * 处理第二个元素
     */
    void processElement2(StreamRecord<IN2> element) throws Exception;
}
