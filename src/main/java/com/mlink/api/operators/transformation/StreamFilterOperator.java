package com.mlink.api.operators.transformation;

import com.mlink.api.functions.transformation.FilterFunction;
import com.mlink.api.operators.AbstractUdfStreamOperator;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.record.StreamRecord;

/**
 * Filter operator，用于执行FilterFunction。满足filter function规则的的记录才会发送给下游
 * Flink原类名为StreamFilter。
 */
public class StreamFilterOperator<IN>
    extends AbstractUdfStreamOperator<IN, FilterFunction<IN>>
    implements OneInputStreamOperator<IN, IN> {

    public StreamFilterOperator(FilterFunction<IN> filterFunction) {
        super(filterFunction);
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        if (userFunction.filter(element.getValue())) {
            output.collect(element);
        }
    }
}
