package com.mlink.api.functions;

import com.mlink.core.configuration.Configuration;
import com.mlink.api.functions.context.RuntimeContext;

/**
 * 该接口相较普通Function提供了更加丰富的操作。RichFunction定义了该函数的生命周期和提供了访问上下文的方法
 */
public interface RichFunction extends Function {

    /**
     * 函数初始化方法，Configuration可以用于获取用户程序的配置。
     */
    void open(Configuration parameters) throws Exception;

    void close() throws Exception;

    RuntimeContext getRuntimeContext();

    void setRuntimeContext(RuntimeContext context);
}
