package com.mlink.runtime.partitioner;

import com.mlink.record.StreamRecord;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 循环将记录发送给下游channel，已达到平均分配的目的
 * @param <T>
 */
public class RebalancePartitioner<T> extends StreamPartitioner<T> {

    private int nextChennelToSendTo;

    @Override
    public void setUp(int numberOfChannels) {
        super.setUp(numberOfChannels);
        //应该是避免每次启动都是从固定channel开始(比如0)
        nextChennelToSendTo = ThreadLocalRandom.current().nextInt(numberOfChannels);
    }

    @Override
    public int selectChannel(StreamRecord<T> record) {
        nextChennelToSendTo = (nextChennelToSendTo + 1) % numberOfChannels;
        return nextChennelToSendTo;
    }

    @Override
    public StreamPartitioner<T> copy() {
        return this;
    }

    @Override
    public String toString() {
        return "REBALANCE";
    }
}
