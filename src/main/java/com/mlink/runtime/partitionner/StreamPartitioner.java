package com.mlink.runtime.partitionner;

import com.mlink.runtime.streamrecord.StreamRecord;
import java.io.Serializable;
import java.util.Objects;

/**
 * Streaming 分区器
 * @param <T>
 */
public abstract class StreamPartitioner<T> implements ChannelSelector<StreamRecord<T>>, Serializable {

    protected int numberOfChannels;

    @Override
    public void setUp(int numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }

    @Override
    public boolean isBroadcast() {
        return false;
    }

    public abstract StreamPartitioner<T> copy();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StreamPartitioner<?> that = (StreamPartitioner<?>) o;
        return numberOfChannels == that.numberOfChannels;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfChannels);
    }
}
