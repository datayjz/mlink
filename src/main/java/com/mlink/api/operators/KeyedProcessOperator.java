package com.mlink.api.operators;

import com.mlink.api.TimeDomain;
import com.mlink.api.timer.TimerService;
import com.mlink.api.common.OutputTag;
import com.mlink.api.functions.KeyedProcessFunction;
import com.mlink.record.StreamRecord;

public class KeyedProcessOperator<KEY, IN, OUT>
    extends AbstractUdfStreamOperator<OUT, KeyedProcessFunction<IN, KEY, OUT>>
    implements OneInputStreamOperator<IN, OUT> {

    private transient TimestampedCollector<OUT> collector;

    private transient ContextImpl context;

    private transient OnTimerContextImpl onTimerContext;


    public KeyedProcessOperator(KeyedProcessFunction<IN, KEY, OUT> keyedProcessFunction) {
        super(keyedProcessFunction);

        chainingStrategy = ChainingStrategy.ALWAYS;
    }

    @Override
    public void open() throws Exception {
        super.open();
        collector = new TimestampedCollector<>(output);

        Interna

    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {

    }

    private class ContextImpl extends KeyedProcessFunction<IN, KEY, OUT>.Context {

        private final TimerService timerService;

        private StreamRecord<IN> element;

        ContextImpl(KeyedProcessFunction<IN, KEY, OUT> function, TimerService timerService) {
            function.super();
            this.timerService = timerService;
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
            return timerService;
        }

        @Override
        public <X> void output(OutputTag<X> outputTag, X value) {
            output.collect(outputTag,
                (StreamRecord<StreamRecord<OUT>>) new StreamRecord<>(value, element.getTimestamp()));
        }

        @Override
        public KEY getCurrentKey() {
            return (KEY) KeyedProcessOperator.this.getCurrentKey();
        }
    }

    private class OnTimerContextImpl extends KeyedProcessFunction<IN, KEY, OUT>.OntimerContext {


        private final TimerService timerService;

        private TimeDomain timeDomain;

        private InternalTimer<KEY, Object> timer;

        OnTimerContextImpl(TimerService timerService) {
            this.timerService = timerService;
        }

        @Override
        public Long timestamp() {
            return timer.getTimestamp();
        }

        @Override
        public TimerService timerServer() {
            return timerService;
        }

        @Override
        public <X> void output(OutputTag<X> outputTag, X value) {
            output.collect(outputTag,
                (StreamRecord<StreamRecord<OUT>>) new StreamRecord<>(value, timer.getTimestamp()));
        }

        @Override
        public TimeDomain timeDomain() {
            return timeDomain;
        }

        @Override
        public KEY getCurrentKey() {
            return timer.getKey();
        }
    }
}
