package com.mlink.api.transformations;

import com.google.common.collect.Lists;
import com.mlink.api.common.OutputTag;
import java.util.Collections;
import java.util.List;

/**
 * 对应一个side output。和PartitionTransform一样，不会创建物理转换算子，仅会影响上下游连接
 * @param <T>
 */
public class SideOutputTransformation<T> extends Transformation<T> {

    private final Transformation<?> input;

    private final OutputTag<T> outputTag;

    public SideOutputTransformation(Transformation<?> input, OutputTag<T> outputTag) {
        super("SideOutput", outputTag.getTypeInfo(), input.getParallelism());
        this.input = input;
        this.outputTag = outputTag;
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

    public OutputTag<T> getOutputTag() {
        return outputTag;
    }
}
