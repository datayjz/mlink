package com.mlink.api.operators.output;

import com.mlink.runtime.streamrecord.StreamRecord;

/**
 * Output包装类，用于为每个发送给下游的记录设置timestamp。目的是保证在算子中传递数据时，element对应的timestamp不会没。
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
        //复用StreamRecord对象
        output.collect(reuse.replace(record));
    }

    public void setTimestamp(StreamRecord<?> timestampBase) {
        if (timestampBase.hasTimestamp()) {
            reuse.setTimestamp(timestampBase.getTimestamp());
        }
    }

    @Override
    public void close() {

    }
}
