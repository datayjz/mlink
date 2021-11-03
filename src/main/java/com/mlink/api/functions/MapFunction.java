package com.mlink.api.functions;

/**
 * map function，接收元素、转换元素，最后发送一个元素。比如做一些parse操作。
 */
public interface MapFunction<IN, OUT> extends Function {

    /**
     * 映射方法，对输入元素进行transform，然后返回发送给下游
     */
    OUT map(IN element) throws Exception;
}
