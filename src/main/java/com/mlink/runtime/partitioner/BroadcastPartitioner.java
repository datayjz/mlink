package com.mlink.runtime.partitioner;

import com.mlink.record.StreamRecord;

/**
 * 数据广播给下游所有并发task，所以会选择所有输出channel
 * @param <T>
 */
public class BroadcastPartitioner<T> extends StreamPartitioner<T>{

    /**
     * 不支持选择单个channel，使用isBroadcast广播
     */
    @Override
    public int selectChannel(StreamRecord<T> record) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBroadcast() {
        return true;
    }

    @Override
    public StreamPartitioner<T> copy() {
        return this;
    }

    @Override
    public String toString() {
        return "BROADCAST";
    }
}
