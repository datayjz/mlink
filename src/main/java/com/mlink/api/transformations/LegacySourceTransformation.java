package com.mlink.api.transformations;

import com.mlink.api.Boundedness;
import com.mlink.api.operators.ChainingStrategy;
import com.mlink.api.operators.factory.SimpleOperatorFactory;
import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.api.operators.source.StreamSourceOperator;
import com.mlink.connector.Source;
import com.mlink.typeinfo.TypeInformation;
import java.util.Collections;
import java.util.List;

/**
 * source connector的Transformation
 * @param <OUT>
 */
public class LegacySourceTransformation<OUT> extends PhysicalTransformation<OUT> implements WithBoundedness {

    private final StreamOperatorFactory<OUT> operatorFactory;

    private Boundedness boundedness;

    //使用默认chaining策略(ALWAYS)
    private ChainingStrategy chainingStrategy = ChainingStrategy.DEFAULT_CHAINING_STRATEGY;

    public LegacySourceTransformation(String name,
                                      StreamSourceOperator<OUT, ?> sourceOperator,
                                      TypeInformation<OUT> outputType,
                                      int parallelism,
                                      Boundedness boundedness) {
        super(name, outputType, parallelism);
        this.operatorFactory = SimpleOperatorFactory.of(sourceOperator);
        this.boundedness = boundedness;
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

    @Override
    public Boundedness getBoundedness() {
        return boundedness;
    }

    public StreamOperatorFactory<OUT> getOperatorFactory() {
        return operatorFactory;
    }
}
