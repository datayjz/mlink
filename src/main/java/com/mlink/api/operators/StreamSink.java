package com.mlink.api.operators;

import com.mlink.api.functions.SinkFunction;
import com.mlink.runtime.streamrecord.StreamRecord;

/**
 * Stream sink operator，用于执行SinkFunction。
 *
 * 实际就是StreamTask运行StreamSink(Operator)，StreamSink收到输入元素后，将其发送给SinkFunction
 */
public class StreamSink<IN>
    extends AbstractUdfStreamOperator<Object, SinkFunction<IN>>
    implements OneInputStreamOperator<IN, Object> {

    private transient SimpleContext<IN> sinkContext;

    public StreamSink(SinkFunction<IN> sinkFunction) {
        //给AbstractUDFStreamOperator，udf operator将function生命周期添加到operator内
        super(sinkFunction);
        //只要允许chain，sink就chaining到上游算子
        chainingStrategy = ChainingStrategy.ALWAYS;
    }

    @Override
    public void open() throws Exception {
        super.open();
        this.sinkContext =  new SimpleContext<>();
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        sinkContext.element = element;
        //调用SinkFunction写到接收器中
        userFunction.invoke(element.getValue(), sinkContext);
    }

    /**
     * SinkContext简单默认实现
     */
    private class SimpleContext<IN> implements SinkFunction.Context {

        private StreamRecord<IN> element;

        //TODO processing time service
        public SimpleContext() {

        }

        @Override
        public long currentProcessingTime() {
            return 0;
        }

        @Override
        public long currentWatermark() {
            return 0;
        }

        @Override
        public Long timestamp() {
            if (element.hasTimestamp()) {
                return element.getTimestamp();
            }
            return null;
        }
    }
}
