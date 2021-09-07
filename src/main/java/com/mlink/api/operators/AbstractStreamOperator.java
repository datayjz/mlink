package com.mlink.api.operators;

import com.mlink.api.functions.context.StreamingRuntimeContext;
import com.mlink.record.StreamRecord;
import com.mlink.state.ValueState;
import com.mlink.state.ValueStateDescriptor;

/**
 * 所有流算子的基类。
 * 如果我们要自定义Operator，需要继承该抽象类(同时实现OneInputStreamOperator和TwoInputStreamOperator)。
 * 如果Operator包含用户自定义Function，也就是UDF，则需要实现AbstractUDFStreamOperator。
 *
 * 该类为所有operator实现了基础功能，比如初始化RuntimeContext，初始化state。
 */
public abstract class AbstractStreamOperator<OUT> implements StreamOperator<OUT> {

    protected transient Output<StreamRecord<OUT>> output;

    private transient StreamingRuntimeContext runtimeContext;

    public void setup() {
        this.runtimeContext = new StreamingRuntimeContext();
    }

    public final void initializeState() {

    }

    public StreamingRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    //TODO
    public  ValueState getPartitionedState(ValueStateDescriptor descriptor ) {
        return null;
    }

    @Override
    public void setCurrentKey(Object key) {

    }

    @Override
    public Object getCurrentKey() {
        return null;
    }
}
