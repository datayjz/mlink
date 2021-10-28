package com.mlink.runtime.partitioner;

import com.mlink.api.functions.KeySelector;
import com.mlink.api.functions.Partitioner;
import com.mlink.record.StreamRecord;

/**
 * 使用用户自定义分区器进行Partitioner分配
 */
public class CustomPartitioner<K, T> extends StreamPartitioner<T> {

    private Partitioner<K> partitioner;
    private KeySelector<T, K> keySelector;

    public CustomPartitioner(Partitioner<K> partitioner, KeySelector<T ,K> keySelector) {
        this.partitioner = partitioner;
        this.keySelector = keySelector;
    }

    @Override
    public int selectChannel(StreamRecord<T> record) {
        K key = keySelector.getKey(record.getValue());
        return partitioner.partitioner(key, numberOfChannels);
    }

    @Override
    public StreamPartitioner<T> copy() {
        return new CustomPartitioner<>(partitioner, keySelector);
    }

    @Override
    public String toString() {
        return "CUSTOM";
    }
}
