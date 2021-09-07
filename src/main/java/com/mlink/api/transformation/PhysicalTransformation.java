package com.mlink.api.transformation;

import com.mlink.api.operators.ChainingStrategy;
import com.mlink.typeinfo.TypeInformation;

/**
 * 用于创建对应物理实际执行operator的Transformation，比如source、map、sink等。可以设置自己的chain策略。
 */
public abstract class PhysicalTransformation<OUT> extends Transformation<OUT> {

    public PhysicalTransformation(String name, TypeInformation<OUT> outputType, int parallelism) {
        super(name, outputType, parallelism);
    }

    public abstract void setChainingStrategy(ChainingStrategy strategy);
}
