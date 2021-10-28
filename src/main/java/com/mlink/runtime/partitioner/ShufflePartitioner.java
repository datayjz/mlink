package com.mlink.runtime.partitioner;

import com.mlink.record.StreamRecord;
import java.util.Random;

/**
 * 纯随机分配
 */
public class ShufflePartitioner<T> extends StreamPartitioner<T> {

    private Random random = new Random();

    @Override
    public int selectChannel(StreamRecord<T> record) {
        return random.nextInt(numberOfChannels);
    }

    @Override
    public StreamPartitioner<T> copy() {
        return this;
    }

    @Override
    public String toString() {
        return "SHUFFLE";
    }
}
