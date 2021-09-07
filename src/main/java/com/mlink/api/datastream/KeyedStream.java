package com.mlink.api.datastream;

import com.mlink.api.functions.KeySelector;

/**
 * KeyedStream是对DataStream进行partition操作后的生成的数据流。通过在DataStream使用KeySelector来指定key分区。
 * DataStream上的典型操作同样可以应用在KeyedStream上，但分区方法除外，比如shuffle、forward、keyBy等。
 *
 * KeyedStream对典型操作进行了重写(比如doTransform、process、sink、source等)，目的是注册keyselector。
 */
public class KeyedStream<IN, KEY> extends DataStream<IN> {

    private final KeySelector<IN, KEY> keySelector;

    public KeyedStream(DataStream<IN> dataStream, KeySelector<IN, KEY> keySelector) {
        super(dataStream.getExecutionEnvironment(), null);
        this.keySelector = keySelector;
    }
}
