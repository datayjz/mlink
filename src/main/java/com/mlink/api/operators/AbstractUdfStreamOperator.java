package com.mlink.api.operators;

import com.mlink.api.functions.Function;
import com.mlink.api.functions.RichFunction;

/**
 * 用于执行UDF的算子基类，该基类会将UDF的open和close添加到Operator的生命周期内
 */
public abstract class AbstractUdfStreamOperator<OUT, F extends Function> extends AbstractStreamOperator<OUT>{

    protected final F userFunction;

    private transient boolean functionClosed = false;

    public AbstractUdfStreamOperator(F userFunction) {
        this.userFunction = userFunction;
    }

    @Override
    public void setup() {
        super.setup();
        if (userFunction instanceof RichFunction) {
            RichFunction richFunction = (RichFunction) userFunction;
            richFunction.setRuntimeContext(getRuntimeContext());
        }
    }

    @Override
    public void open() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void dispose() {

    }
}
