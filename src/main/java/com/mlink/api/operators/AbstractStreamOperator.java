package com.mlink.api.operators;

import com.mlink.api.functions.context.StreamingRuntimeContext;
import com.mlink.api.operators.output.Output;
import com.mlink.runtime.streamrecord.StreamRecord;
import com.mlink.runtime.tasks.StreamTask;

/**
 * 所有stream operator 基类实现，具体实现operator除了继承该抽象类(或子类AbstractUDFStreamOperator)
 * 还需要实现OneInputStreamOperator和TwoInputStreamOperator来标识是该operator是一元运算还是二元运算。
 * 如果Operator包含用户自定义Function，也就是UDF，则需要实现AbstractUDFStreamOperator。
 *
 * 该类为所有operator实现了基础功能，比如初始化RuntimeContext，初始化state。
 */
public abstract class AbstractStreamOperator<OUT> implements StreamOperator<OUT> {

    //大多数算子的默认chain策略，也就是从当前算子向后chain
    protected ChainingStrategy chainingStrategy = ChainingStrategy.HEAD;

    //运行次operator以及chain op的task
    private transient StreamTask<?, ?> container;

    //用于发送数据和控制事件
    protected transient Output<StreamRecord<OUT>> output;

    //udf运行时上下文
    private transient StreamingRuntimeContext runtimeContext;


    //注意：task并不是operator创建时复制的，而是调用该setup方法，实际在构建StreamGraph传递的
    public void setup(StreamTask<?, ?> containingTask,
                      Output<StreamRecord<OUT>> output) {
        this.container = containingTask;
        this.output = output;

        this.runtimeContext = new StreamingRuntimeContext();
    }

    //一下三个目前是空实现
    @Override
    public void open() throws Exception {
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void dispose() {
    }

    public StreamingRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    //operator chaining strategy config
    public void setChainingStrategy(ChainingStrategy chainingStrategy) {
        this.chainingStrategy = chainingStrategy;
    }

    public ChainingStrategy getChainingStrategy() {
        return chainingStrategy;
    }
}
