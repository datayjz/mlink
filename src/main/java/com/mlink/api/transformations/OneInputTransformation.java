package com.mlink.api.transformations;

import com.google.common.collect.Lists;
import com.mlink.api.functions.KeySelector;
import com.mlink.api.operators.ChainingStrategy;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.api.operators.factory.SimpleOperatorFactory;
import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.typeinfo.TypeInformation;
import java.util.Collections;
import java.util.List;

/**
 * OneInputTransformation对应OneInputStreamOperator
 */
public class OneInputTransformation<IN, OUT> extends PhysicalTransformation<OUT> {

    private final Transformation<IN> input;

    private final StreamOperatorFactory<OUT> operatorFactory;

    private KeySelector<IN, ?> keySelector;

    public OneInputTransformation(Transformation<IN> input,
                                  String name,
                                  OneInputStreamOperator<IN, OUT> oneInputStreamOperator,
                                  TypeInformation<OUT> outputType,
                                  int parallelism) {

        this(input, name, SimpleOperatorFactory.of(oneInputStreamOperator), outputType, parallelism);
    }

    public OneInputTransformation(Transformation<IN> input,
                                  String name,
                                  StreamOperatorFactory<OUT> operatorFactory,
                                  TypeInformation<OUT> outputType,
                                  int parallelism) {
        super(name, outputType, parallelism);
        this.input = input;
        this.operatorFactory = operatorFactory;
    }

    @Override
    public void setChainingStrategy(ChainingStrategy strategy) {
        operatorFactory.setChainingStrategy(strategy);
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
}
