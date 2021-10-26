package com.mlink.api.transformation;

import com.google.common.collect.Lists;
import com.mlink.api.operators.ChainingStrategy;
import com.mlink.connector.Sink;
import java.util.Collections;
import java.util.List;

/**
 * sink connector的Transformation
 * @param <IN>
 */
public class SinkTransformation<IN> extends PhysicalTransformation<Object> {

    private final Transformation<IN> input;

    private final Sink<IN> sinkConnector;

    private ChainingStrategy chainingStrategy;

    public SinkTransformation(Transformation<IN> input,
                              String name,
                              Sink<IN> sinkConnector,
                              int parallelism) {
        //TODO type
        super(name, null, parallelism);
        this.input = input;
        this.sinkConnector = sinkConnector;
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
        this.chainingStrategy = strategy;
    }

    public ChainingStrategy getChainingStrategy() {
        return chainingStrategy;
    }
}
