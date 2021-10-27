package com.mlink.api.transformations;

import com.google.common.collect.Lists;
import com.mlink.api.operators.ChainingStrategy;
import com.mlink.api.operators.TwoInputStreamOperator;
import com.mlink.api.operators.factory.SimpleOperatorFactory;
import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.typeinfo.TypeInformation;
import java.util.List;

/**
 * TwoInputTransformation对应TwoInputStreamOperator。接收两个输入Transformation，输出一个流。
 */
public class TwoInputTransformation<IN1, IN2, OUT> extends PhysicalTransformation<OUT> {

    private final Transformation<IN1> input1;
    private final Transformation<IN2> input2;

    private final StreamOperatorFactory<OUT> operatorFactory;

    public TwoInputTransformation(Transformation<IN1> input1,
                                  Transformation<IN2> input2,
                                  String name,
                                  TwoInputStreamOperator<IN1, IN2, OUT> twoInputStreamOperator,
                                  TypeInformation<OUT> outputType,
                                  int parallelism) {
        this(input1, input2, name, SimpleOperatorFactory.of(twoInputStreamOperator), outputType,
            parallelism);
    }

    public TwoInputTransformation(Transformation<IN1> input1,
                                  Transformation<IN2> input2,
                                  String name,
                                  StreamOperatorFactory<OUT> operatorFactory,
                                  TypeInformation<OUT> outputType,
                                  int parallelism) {
        super(name, outputType, parallelism);
        this.input1 = input1;
        this.input2 = input2;
        this.operatorFactory = operatorFactory;
    }

    public Transformation<IN1> getInput1() {
        return input1;
    }

    public Transformation<IN2> getInput2() {
        return input2;
    }

    @Override
    public void setChainingStrategy(ChainingStrategy strategy) {
        this.operatorFactory.setChainingStrategy(strategy);
    }

    @Override
    public List<Transformation<?>> getTransitivePredecessors() {
        List<Transformation<?>> result = Lists.newArrayList();
        result.add(this);
        result.addAll(input1.getTransitivePredecessors());
        result.addAll(input1.getTransitivePredecessors());
        return result;
    }

    @Override
    public List<Transformation<?>> getInputs() {
        return Lists.newArrayList(input1, input2);
    }
}
