package com.mlink.api.transformations;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/**
 * 多个相同数据类型的输入流合并，该Transformation并没有继承PhysicalTransformation，它不会创建物理算子，仅用来表示上游算子和下游算子的连接
 */
public class UnionTransformation<OUT> extends Transformation<OUT> {

    private final List<Transformation<OUT>> inputs;

    public UnionTransformation(List<Transformation<OUT>> inputs) {
        super("Union", inputs.get(0).getOutputType(), inputs.get(0).getParallelism());

        /**
         * 所以需要待union的stream都是相同数据类型
         */
        for (Transformation<OUT> input : inputs) {
            if (!input.getOutputType().equals(getOutputType())) {
                throw new UnsupportedOperationException();
            }
        }

        this.inputs = Lists.newArrayList(inputs);
    }

    @Override
    public List<Transformation<?>> getTransitivePredecessors() {
        List<Transformation<?>> result = Lists.newArrayList();
        result.add(this);
        for (Transformation<OUT> input : inputs) {
            result.addAll(input.getTransitivePredecessors());
        }
        return result;
    }

    @Override
    public List<Transformation<?>> getInputs() {
        return new ArrayList<>(inputs);
    }
}
