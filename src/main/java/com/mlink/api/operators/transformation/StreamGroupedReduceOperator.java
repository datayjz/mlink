package com.mlink.api.operators.transformation;

import com.mlink.api.functions.transformation.ReduceFunction;
import com.mlink.api.operators.AbstractUdfStreamOperator;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.record.StreamRecord;
import com.mlink.state.ValueState;
import com.mlink.state.ValueStateDescriptor;

/**
 * Reduce operator，执行ReduceFunction。借助ValueState实现记录历史数据。
 * Flink原类叫StreamGroupReduceOperator。
 */
public class StreamGroupedReduceOperator<IN>
    extends AbstractUdfStreamOperator<IN, ReduceFunction<IN>>
    implements OneInputStreamOperator<IN, IN> {

    //TODO 实现state后
    private transient ValueState<IN> values;

    public StreamGroupedReduceOperator(ReduceFunction<IN> reduceFunction) {
        super(reduceFunction);
    }

    @Override
    public void open() throws Exception {
        super.open();
        //TODO 实现state后，通过Descriptor来创建state
        ValueStateDescriptor<IN> descriptor = new ValueStateDescriptor();
        //从StreamOperator中获取分区state
        values = getPartitionedState(descriptor);
    }

    @Override
    public void processElement(StreamRecord<IN> element) throws Exception {
        IN value = element.getValue();
        //TODO from state get
        IN currentValue = values.value();

        if (currentValue != null) {
            //reduce 计算
            IN reduced = userFunction.reduce(currentValue, value);
            //计算新值更新state
            values.update(reduced);
            output.collect(element.replace(reduced));
        } else {
            //没有历史值则不执行reduce操作
            values.update(value);
            output.collect(element.replace(value));
        }
    }
}
