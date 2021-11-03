package com.mlink.api.operators;

import com.mlink.api.functions.FlatMapFunction;
import com.mlink.api.operators.output.TimestampedCollector;
import com.mlink.runtime.streamrecord.StreamRecord;

/**
 * Flatmap operator，用于执行FlatMapFunction。通过Collector收集flat的记录。
 */
public class StreamFlatMap<IN, OUT> extends AbstractUdfStreamOperator<OUT,
    FlatMapFunction<IN, OUT>> implements OneInputStreamOperator<IN, OUT> {

    private transient TimestampedCollector<OUT> collector;

    public StreamFlatMap(FlatMapFunction<IN, OUT> flatMapFunction) {
        //将Function给AbstractUdfStreamOperator，将function声明周期添加到operator
        super(flatMapFunction);
    }

    @Override
    public void open() throws Exception {
        super.open();
        this.collector = new TimestampedCollector<>(output);
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        //因为不复用传递进来的StreamRecord了，所以需要记录该element
        // 的timestamp，在transform之后封装成新StreamRecord后把timestamp补上
        collector.setTimestamp(element);
        //上游获取的StreamRecord记录和operator output给FlatMapFunction来执行转换
        userFunction.flatMap(element.getValue(), collector);
    }
}
