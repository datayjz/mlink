package com.mlink.api.transformations;

import com.google.common.collect.Lists;
import com.mlink.api.operators.ChainingStrategy;
import com.mlink.api.operators.factory.SimpleOperatorFactory;
import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.api.operators.sink.StreamSinkOperator;
import java.util.Collections;
import java.util.List;

/**
 * sink connector的Transformation
 * @param <IN>
 */
public class LegacySinkTransformation<IN> extends PhysicalTransformation<Object> {

    private final Transformation<IN> input;

    private final StreamOperatorFactory<Object> operatorFactory;

    public LegacySinkTransformation(Transformation<IN> input,
                                    String name,
                                    StreamSinkOperator<IN> sinkOperator,
                                    int parallelism) {
        //sink无输出，所以输出类型为null
        super(name, null, parallelism);
        this.input = input;
        this.operatorFactory = SimpleOperatorFactory.of(sinkOperator);
    }

    @Override
    public List<Transformation<?>> getTransitivePredecessors() {
        final List<Transformation<?>> result = Lists.newArrayList();
        result.add(this);
        result.addAll(input.getTransitivePredecessors());
        return result;
    }

    @Override
    public List<Transformation<?>> getInputs() {
        return Collections.singletonList(input);
    }

    @Override
    public void setChainingStrategy(ChainingStrategy strategy) {
        operatorFactory.setChainingStrategy(strategy);
    }

    public StreamOperatorFactory<Object> getOperatorFactory() {
        return operatorFactory;
    }
}
