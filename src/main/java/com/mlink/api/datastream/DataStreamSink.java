package com.mlink.api.datastream;

import com.mlink.api.operators.sink.StreamSinkOperator;

public class DataStreamSink<IN> {

    public DataStreamSink(DataStream<IN> inputStream, StreamSinkOperator<IN> sinkOperator) {

    }
}
