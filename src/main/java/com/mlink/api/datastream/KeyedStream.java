package com.mlink.api.datastream;

import com.mlink.api.functions.KeySelector;
import com.mlink.api.functions.ReduceFunction;
import com.mlink.runtime.partitionner.KeyGroupRangeAssignment;
import com.mlink.runtime.partitionner.KeyGroupStreamPartitioner;
import com.mlink.api.transformations.PartitionTransformation;
import com.mlink.api.transformations.ReduceTransformation;

/**
 * KeyedStream是对DataStream进行partition操作后的生成的数据流。通过在DataStream使用KeySelector来指定key分区。
 * DataStream上的典型操作同样可以应用在KeyedStream上，但分区方法除外，比如shuffle、forward、keyBy等。
 *
 * KeyedStream对典型操作进行了重写(比如doTransform、process、sink、source等)，目的是注册keyselector。
 */
public class KeyedStream<IN, KEY> extends DataStream<IN> {

    private final KeySelector<IN, KEY> keySelector;

    public KeyedStream(DataStream<IN> inputStream, KeySelector<IN, KEY> keySelector) {
        //创建对应的PartitionTransformation，而PartitionTransformation需要给出具体的分区器，keyBy就是使用KeyGroup shuffle
        super(inputStream.getExecutionEnvironment(),
            new PartitionTransformation<>(inputStream.getTransformation(),
                new KeyGroupStreamPartitioner<>(keySelector, KeyGroupRangeAssignment.DEFAULT_LOWER_BOUND_MAX_PARALLELISM)));
        //这里虽然使用了默认最大并发度，实际在StreamTask里面会对其进行重新配置，根据配置中设置的最大并发度
        this.keySelector = keySelector;
    }

    public KeySelector<IN, KEY> getKeySelector() {
        return keySelector;
    }

    public SingleOutputStreamOperator<IN> reduce(ReduceFunction<IN> reducer) {
        //注意这里reduce并没有创建Operator给Transformation，而是直接把function给了Transformation，因为在做StreamGraph
        // 生成时，根据处理模式，有多种reduce operator
        ReduceTransformation<IN, KEY> reduceTransformation = new ReduceTransformation<>(
            "Keyed Reduce",
            environment.getParallelism(),
            transformation,
            reducer,
            keySelector);
        return new SingleOutputStreamOperator<>(environment, reduceTransformation);
    }

}
