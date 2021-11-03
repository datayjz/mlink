package com.mlink.api.operators;

import com.mlink.api.eventtime.TimeCharacteristic;
import com.mlink.api.functions.SourceFunction;
import com.mlink.api.operators.output.Output;
import com.mlink.runtime.streamrecord.StreamRecord;

/**
 * 根据时间类型，来创建SourceContext
 */
public class StreamSourceContext {

    public static <OUT> SourceFunction.SourceContext<OUT> getSourceContext(
        TimeCharacteristic timeCharacteristic,
        Output<StreamRecord<OUT>> output) {

        final SourceFunction.SourceContext<OUT> sourceContext;
        //TODO 不同时间类型的context实现
        switch (timeCharacteristic) {
            case EventTime:
            case IngestionTime:
            case ProcessingTime:
                sourceContext = new NonTimestampContext<>(output);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return sourceContext;
    }


    /**
     * 用于Processing time的context，将-1赋值给所有StreamRecord的timestamp，并且不转发Watermark
     */
    private static class NonTimestampContext<T> implements SourceFunction.SourceContext<T> {

        private final Output<StreamRecord<T>> output;
        //重复使用的记录对象，读取到数据后封装到该对象内，然后发送给下游
        private final StreamRecord<T> reuse;

        private NonTimestampContext(Output<StreamRecord<T>> output) {
            this.output = output;
            this.reuse = new StreamRecord<>(null);
        }

        @Override
        public void collect(T element) {
            output.collect(reuse.replace(element));
        }

        @Override
        public void close() {
        }
    }

}
