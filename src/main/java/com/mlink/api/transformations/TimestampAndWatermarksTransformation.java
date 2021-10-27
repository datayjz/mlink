package com.mlink.api.transformations;

import com.google.common.collect.Lists;
import com.mlink.api.eventtime.WatermarkStrategy;
import com.mlink.api.operators.ChainingStrategy;
import java.util.Collections;
import java.util.List;

/**
 * 为DataStream.assignTimestampsAndWatermarks创建的Transformation
 */
public class TimestampAndWatermarksTransformation<IN> extends PhysicalTransformation<IN> {

    private final Transformation<IN> input;
    private final WatermarkStrategy<IN> watermarkStrategy;

    private ChainingStrategy chainingStrategy = ChainingStrategy.DEFAULT_CHAINING_STRATEGY;

    public TimestampAndWatermarksTransformation(String name,
                                                int parallelism,
                                                Transformation<IN> input,
                                                WatermarkStrategy<IN> watermarkStrategy) {
        super(name, input.getOutputType(), parallelism);
        this.input = input;
        this.watermarkStrategy = watermarkStrategy;
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

    @Override
    public void setChainingStrategy(ChainingStrategy strategy) {
        this.chainingStrategy = strategy;
    }

    public ChainingStrategy getChainingStrategy() {
        return chainingStrategy;
    }

    public WatermarkStrategy<IN> getWatermarkStrategy() {
        return watermarkStrategy;
    }
}
