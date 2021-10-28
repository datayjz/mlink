package com.mlink.runtime.partitioner;

import com.mlink.record.StreamRecord;

/**
 * 和RebalancePartitioner类似，同样通过round-robin模式将元素均匀分给下游，但是相较RebalancePartitioner
 *   ，该算法取决于上下游并发，比如上游并发2，下游并发4，则第一个并发固定向下游两个算子并发发送元素，另一个同理。(Partitioner实现细节没看出差异)
 */
public class RescalePartitioner<T> extends StreamPartitioner<T> {

    private int nextChannelToSendTo = -1;

    @Override
    public int selectChannel(StreamRecord<T> record) {
        if (++nextChannelToSendTo >= numberOfChannels) {
            nextChannelToSendTo = 0;
        }
        return nextChannelToSendTo;
    }

    @Override
    public StreamPartitioner<T> copy() {
        return this;
    }

    @Override
    public String toString() {
        return "RESCALE";
    }
}
