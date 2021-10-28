package com.mlink.runtime.partitioner;

import com.mlink.record.StreamRecord;

/**
 * 将所有元素发送到下游算子taskId = 0的operator上
 * @param <T>
 */
public class GlobalPartitioner<T> extends StreamPartitioner<T> {

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
        return "GLOBAL";
    }
}
