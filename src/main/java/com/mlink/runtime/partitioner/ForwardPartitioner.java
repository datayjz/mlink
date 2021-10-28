package com.mlink.runtime.partitioner;

import com.mlink.record.StreamRecord;

/**
 * 用于将元素发送到本地下游算子task上
 */
public class ForwardPartitioner<T> extends StreamPartitioner<T>{

    @Override
    public int selectChannel(StreamRecord<T> record) {
        return 0;
    }

    @Override
    public StreamPartitioner<T> copy() {
        return this;
    }

    @Override
    public String toString() {
        return "FORWARD";
    }
}
