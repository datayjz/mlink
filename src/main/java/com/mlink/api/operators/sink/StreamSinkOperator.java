package com.mlink.api.operators.sink;

import com.mlink.api.functions.sink.SinkFunction;
import com.mlink.api.operators.AbstractUdfStreamOperator;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.record.StreamRecord;

public class StreamSinkOperator<IN>
    extends AbstractUdfStreamOperator<Object, SinkFunction<IN>>
    implements OneInputStreamOperator<IN, Object> {

    private transient SinkFunction.Context context;

    public StreamSinkOperator(SinkFunction<IN> sinkFunction) {
        super(sinkFunction);
    }

    @Override
    public void open() throws Exception {
        super.open();
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        userFunction.invoke(element.getValue(), context);
    }
}
