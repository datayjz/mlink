package com.mlink.api;

import com.mlink.api.timer.TimerService;
import com.mlink.api.common.OutputTag;
import com.mlink.api.eventtime.Watermark;
import com.mlink.api.functions.ProcessFunction;
import com.mlink.api.operators.AbstractUdfStreamOperator;
import com.mlink.api.operators.ChainingStrategy;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.api.operators.TimestampedCollector;
import com.mlink.record.StreamRecord;
import com.mlink.runtime.tasks.ProcessingTimeService;

public class ProcessOperator<IN, OUT>
    extends AbstractUdfStreamOperator<OUT, ProcessFunction<IN, OUT>>
    implements OneInputStreamOperator<IN, OUT> {

    //output 包装类，为每条记录都设置时间戳
    private transient TimestampedCollector<OUT> collector;

    private transient ContextImpl context;

    private long currentWatermark = Long.MIN_VALUE;

    public ProcessOperator(ProcessFunction<IN, OUT> function) {
        super(function);

        chainingStrategy = ChainingStrategy.ALWAYS;
    }

    @Override
    public void open() throws Exception {
        super.open();
        this.collector = new TimestampedCollector<>(output);
        this.context = new ContextImpl(userFunction, getProcessingTimeService());
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        collector.setTimestamp(element);
        context.element = element;
        //调用processFunction的处理方法处理
        userFunction.processElement(element.getValue(), context, collector);
        context.element = null;
    }

    @Override
    public void processWatermark(Watermark watermark) throws Exception {
        super.processWatermark(watermark);
        this.currentWatermark = watermark.getTimestamp();
    }

    private class ContextImpl extends ProcessFunction<IN, OUT>.Context implements TimerService {

        private StreamRecord<IN> element;

        private final ProcessingTimeService processingTimeService;

        ContextImpl(ProcessFunction<IN, OUT> function,
                    ProcessingTimeService processingTimeService) {
            function.super();
            this.processingTimeService = processingTimeService;
        }

        @Override
        public Long timestamp() {
            if (element.hasTimestamp()) {
                return element.getTimestamp();
            } else {
                return null;
            }
        }

        @Override
        public TimerService timerServer() {
            return this;
        }

        @Override
        public <X> void output(OutputTag<X> outputTag, X value) {
            output.collect(outputTag,
                (StreamRecord<StreamRecord<OUT>>) new StreamRecord<>(value, element.getTimestamp()));
        }

        //---------- TimerServer方法----------//
        @Override
        public long currentProcessingTime() {
            return processingTimeService.getCurrentProcessingTime();
        }

        @Override
        public long currentWatermark() {
            return currentWatermark;
        }

        @Override
        public void registerProcessingTimeTimer(long time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void registerEventTimeTimer(long time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteProcessingTimeTimer(long time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteEventTimeTimer(long time) {
            throw new UnsupportedOperationException();
        }
    }
}
