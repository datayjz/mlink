package com.mlink.api.operators.source;

import com.mlink.api.eventtime.TimeCharacteristic;
import com.mlink.api.functions.source.SourceFunction;

/**
 * 根据时间类型，来创建SourceContext
 */
public class StreamSourceContext {

    public static <OUT>SourceFunction.SourceContext<OUT> getSourceContext(
        TimeCharacteristic timeCharacteristic) {

        final SourceFunction.SourceContext<OUT> context;

        switch (timeCharacteristic) {
            case EventTime:
            case IngestionTime:
            case ProcessingTime:
            default:
                throw new IllegalArgumentException();
        }
    }

    public static class NonTimestampContext<T> implements SourceFunction.SourceContext<T> {

        @Override
        public void collect(T element) {

        }

        @Override
        public void close() {

        }
    }

}
