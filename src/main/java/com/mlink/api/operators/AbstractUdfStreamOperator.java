package com.mlink.api.operators;

import com.mlink.api.functions.Function;
import com.mlink.api.operators.output.Output;
import com.mlink.runtime.streamrecord.StreamRecord;
import com.mlink.runtime.tasks.StreamTask;

/**
 * 用于执行UDF的算子基类，该基类会将UDF的open和close添加到Operator的生命周期内
 */
public abstract class AbstractUdfStreamOperator<OUT, F extends Function> extends AbstractStreamOperator<OUT>{

    //udf
    protected final F userFunction;

    private transient boolean functionClosed = false;

    public AbstractUdfStreamOperator(F userFunction) {
        this.userFunction = userFunction;
    }

    @Override
    public void setup(StreamTask<?, ?> streamTask,
                      Output<StreamRecord<OUT>> output) {

        super.setup(streamTask, output);

        //如果是RichFunction需要为Function传递StreamRuntimeContext
//        if (userFunction instanceof RichFunction) {
//            RichFunction richFunction = (RichFunction) userFunction;
//            richFunction.setRuntimeContext(getRuntimeContext());
//        }
    }

    @Override
    public void open() throws Exception {
        super.open();
    }

    @Override
    public void close() throws Exception {
        super.close();
        functionClosed = true;
        //RichFunction close
    }

    @Override
    public void dispose() {
        super.dispose();
        if (!functionClosed) {
            functionClosed = true;
            //RichFunction close
        }
    }
}
