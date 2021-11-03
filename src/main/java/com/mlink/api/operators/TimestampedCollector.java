package com.mlink.api.operators;

import com.mlink.api.common.OutputTag;
import com.mlink.record.StreamRecord;

/**
 * Output 的wrapper，用于为每条记录都设置时间戳
 * @param <T>
 */
public class TimestampedCollector<T> implements Output<T> {

    private final Output<StreamRecord<T>> output;

    private final StreamRecord<T> reuse;

    public TimestampedCollector(Output<StreamRecord<T>> output) {
        this.output = output;
        this.reuse = new StreamRecord<>(null);
    }

    @Override
    public void collect(T record) {
        output.collect(reuse.replace(record));
    }

    public void setTimestamp(StreamRecord<?> timestampBase) {
        if (timestampBase.hasTimestamp()) {
            reuse.setTimestamp(timestampBase.getTimestamp());
        }
    }

    @Override
    public <X> void collect(OutputTag<X> outputTag, StreamRecord<T> record) {
        output.collect(outputTag, (StreamRecord<StreamRecord<T>>) record);
    }

    @Override
    public void close() {
        output.close();
    }
}
