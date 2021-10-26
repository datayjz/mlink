package com.mlink.api.transformation;

import com.mlink.api.operators.ChainingStrategy;
import com.mlink.connector.Source;
import com.mlink.typeinfo.TypeInformation;
import java.util.Collections;
import java.util.List;

/**
 * source connector的Transformation
 * @param <OUT>
 */
public class SourceTransformation<OUT> extends PhysicalTransformation<OUT>{

    private final Source<OUT> sourceConnector;

    //使用默认chaining策略(ALWAYS)
    private ChainingStrategy chainingStrategy = ChainingStrategy.DEFAULT_CHAINING_STRATEGY;

    public SourceTransformation(String name,
                                Source<OUT> sourceConnector,
                                TypeInformation<OUT> outputType,
                                int parallelism) {
        super(name, outputType, parallelism);
        this.sourceConnector = sourceConnector;
    }

    @Override
    public List<Transformation<?>> getTransitivePredecessors() {
        return Collections.singletonList(this);
    }

    @Override
    public List<Transformation<?>> getInputs() {
        return Collections.emptyList();
    }

    @Override
    public void setChainingStrategy(ChainingStrategy chainingStrategy) {
        this.chainingStrategy = chainingStrategy;
    }

    public ChainingStrategy getChainingStrategy() {
        return chainingStrategy;
    }
}
