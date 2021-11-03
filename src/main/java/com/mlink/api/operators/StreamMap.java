package com.mlink.api.operators;

import com.mlink.api.functions.MapFunction;
import com.mlink.runtime.streamrecord.StreamRecord;

/**
 * Map Operator，用于执行MapFunction逻辑。
 */
public class StreamMap<IN, OUT> extends AbstractUdfStreamOperator<OUT, MapFunction<IN, OUT>>
    implements OneInputStreamOperator<IN, OUT> {

    public StreamMap(MapFunction<IN, OUT> mapFunction) {
        //将Function给AbstractUdfStreamOperator，将function声明周期添加到operator
        super(mapFunction);
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        OUT result = userFunction.map(element.getValue());
        output.collect(element.replace(result));
    }
}
