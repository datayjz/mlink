package com.mlink.api.transformations;

import com.mlink.api.operators.ChainingStrategy;

/**
 * 该Transformation对应物理的operator，比如map、source等。与之对应的partition、union不会对应物理算子，它们的Transformation
 * 只是用于保证上下游按照指定方式连接。
 */
public abstract class PhysicalTransformation<OUT> extends Transformation<OUT> {

    public PhysicalTransformation(String name, int parallelism) {
        super(name, parallelism);
    }

    public abstract void setChainingStrategy(ChainingStrategy strategy);
}
