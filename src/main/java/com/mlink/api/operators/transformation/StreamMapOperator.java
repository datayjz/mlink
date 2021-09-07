package com.mlink.api.operators.transformation;

import com.mlink.api.functions.transformation.MapFunction;
import com.mlink.api.operators.AbstractUdfStreamOperator;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.record.StreamRecord;

/**
 * Map Operator，用于执行MapFunction逻辑。
 * Flink原类StreamMap。
 */
public class StreamMapOperator<IN, OUT> extends AbstractUdfStreamOperator<OUT,
    MapFunction<IN, OUT>> implements OneInputStreamOperator<IN, OUT> {


    public StreamMapOperator(MapFunction<IN, OUT> mapFunction) {
        super(mapFunction);
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        OUT result = userFunction.map(element.getValue());
        output.collect(element.replace(result));
    }
}
