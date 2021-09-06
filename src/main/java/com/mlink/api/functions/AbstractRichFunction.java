package com.mlink.api.functions;

import com.mlink.core.configuration.Configuration;
import com.mlink.api.functions.context.RuntimeContext;

/**
 * RichFunction抽象实现基类。该类对context设置进行了默认实现，open和close默认为空。
 */
public abstract class AbstractRichFunction implements RichFunction{

    private transient RuntimeContext runtimeContext;

    @Override
    public void open(Configuration parameters) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public RuntimeContext getRuntimeContext() {
        if (this.runtimeContext != null) {
            return this.runtimeContext;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void setRuntimeContext(RuntimeContext context) {
        this.runtimeContext = context;
    }
}
