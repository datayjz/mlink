package com.mlink.api.operators.transformation;

import com.mlink.api.functions.transformation.FlatMapFunction;
import com.mlink.api.operators.AbstractUdfStreamOperator;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.record.StreamRecord;
import com.mlink.util.Collector;

/**
 * Flatmap operator，用于执行FlatMapFunction。通过Collector收集flat的记录。
 * Flink原类为StreamFlatMap
 */
public class StreamFlatMapOperator<IN, OUT> extends AbstractUdfStreamOperator<OUT,
    FlatMapFunction<IN, OUT>> implements OneInputStreamOperator<IN, OUT> {

    private transient Collector<OUT> collector;

    public StreamFlatMapOperator(FlatMapFunction<IN, OUT> flatMapFunction) {
        super(flatMapFunction);
    }

    @Override
    public void open() throws Exception {
        super.open();
        //TODO init collector
        this.collector = null;
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        userFunction.flatMap(element.getValue(), collector);
    }
}
