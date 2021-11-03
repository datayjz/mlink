package com.mlink.api.operators;

import com.mlink.runtime.streamrecord.StreamRecord;

/**
 * 与OneInputStreamOperator对应，用于处理具有两个输入流的算子。
 */
public interface TwoInputStreamOperator<IN1, IN2, OUT> extends StreamOperator<OUT>{

    /**
     * 处理到达此双输入operator的第一个输入流的元素
     */
    void processElement1(StreamRecord<IN1> element) throws Exception;

    /**
     * 处理到达次双输入的operator的第二个输入的元素
     */
    void processElement2(StreamRecord<IN2> element) throws Exception;

}
