package com.mlink.runtime.partitionner;

import com.mlink.api.functions.KeySelector;
import com.mlink.runtime.streamrecord.StreamRecord;

public class KeyGroupStreamPartitioner<T, K> extends StreamPartitioner<T> {

    private final KeySelector<T, K> keySelector;

    private int maxParallelism;

    public KeyGroupStreamPartitioner(KeySelector<T, K> keySelector, int maxParallelism) {
        this.keySelector = keySelector;
        this.maxParallelism = maxParallelism;
    }


    @Override
    public int selectChannel(StreamRecord<T> record) {
        K key = keySelector.getKey(record.getValue());

        return KeyGroupRangeAssignment.assignKeyToParallelismOperator(key, numberOfChannels, maxParallelism);
    }

    @Override
    public StreamPartitioner<T> copy() {
        return this;
    }

    @Override
    public String toString() {
        return "HASH";
    }
}
