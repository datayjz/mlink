package com.mlink.api.functions.sink;

import com.mlink.api.functions.AbstractRichFunction;

/**
 * 用于Sink Function的RichFunction，该抽象类聚合了AbstractRichFunction和实现SinkFunction接口功能。
 */
public abstract class SinkRichFunction<IN> extends AbstractRichFunction implements SinkFunction<IN> {

}
