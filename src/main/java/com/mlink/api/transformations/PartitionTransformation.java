package com.mlink.api.transformations;

import com.google.common.collect.Lists;
import com.mlink.runtime.partitioner.StreamPartitioner;
import java.util.Collections;
import java.util.List;

/**
 * 该Transformation代表代表对输入元素进行分区
 * 和UnionTransformation一样，不会对应一个物理算子，用以连接上下游算子。
 */
public class PartitionTransformation<OUT> extends Transformation<OUT> {

    private final Transformation<OUT> input;

    private final StreamPartitioner<OUT> partitioner;

    private final StreamExchangeMode exchangeMode;

    public PartitionTransformation(Transformation<OUT> input,
                                   StreamPartitioner<OUT> partitioner) {
        this(input, partitioner, StreamExchangeMode.UNDEFINED);
    }

    public PartitionTransformation(Transformation<OUT> input,
                                   StreamPartitioner<OUT> partitioner,
                                   StreamExchangeMode exchangeMode) {

        super("Partition", input.getOutputType(), input.getParallelism());

        this.input = input;
        this.partitioner = partitioner;
        this.exchangeMode = exchangeMode;
    }

    @Override
    public List<Transformation<?>> getTransitivePredecessors() {
        List<Transformation<?>> result = Lists.newArrayList();
        result.add(this);
        result.addAll(input.getTransitivePredecessors());
        return result;
    }

    @Override
    public List<Transformation<?>> getInputs() {
        return Collections.singletonList(input);
    }

    public StreamPartitioner<OUT> getPartitioner() {
        return partitioner;
    }

    public StreamExchangeMode getExchangeMode() {
        return exchangeMode;
    }
}
