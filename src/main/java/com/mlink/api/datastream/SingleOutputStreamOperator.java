package com.mlink.api.datastream;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.operators.ChainingStrategy;
import com.mlink.api.transformations.PhysicalTransformation;
import com.mlink.api.transformations.Transformation;

/**
 * 单流操作
 */
public class SingleOutputStreamOperator<T> extends DataStream<T> {

    public SingleOutputStreamOperator(StreamExecutionEnvironment environment,
                                      Transformation<T> transformation) {
        super(environment, transformation);
    }

    public String getName() {
        return transformation.getName();
    }

    public SingleOutputStreamOperator<T> name(String name) {
        transformation.setName(name);
        return this;
    }

    public SingleOutputStreamOperator<T> setParallelism(int parallelism) {
        transformation.setParallelism(parallelism);
        return this;
    }

    public SingleOutputStreamOperator<T> setMaxParallelism(int maxParallelism) {
        transformation.setMaxParallelism(maxParallelism);
        return this;
    }

    //-------- operator chaining ----------//
    public SingleOutputStreamOperator<T> setChainingStrategy(ChainingStrategy strategy) {
        if (transformation instanceof PhysicalTransformation) {
            ((PhysicalTransformation<T>) transformation).setChainingStrategy(strategy);
        }
        return this;
    }

    public SingleOutputStreamOperator<T> disableChaining() {
        return setChainingStrategy(ChainingStrategy.NEVER);
    }

    public SingleOutputStreamOperator<T> startNewChain() {
        return setChainingStrategy(ChainingStrategy.HEAD);
    }
}
