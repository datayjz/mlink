package com.mlink.api.graph.streamgraph.translator;

import com.mlink.api.transformations.Transformation;
import java.util.Collection;

/**
 * 所有TransformationTranslator的基类，这个类设计的比较绕，将translateForBatch和translateForStreaming
 * 转换为该类内部定义的translateForBatchInternal和translateForStreamingInternal。
 *
 */
public abstract class SimpleTransformationTranslator<OUT, T extends Transformation<OUT>>
    implements TransformationTranslator<OUT, T> {

    //返回集合为该Transformation对应的输出节点
    @Override
    public Collection<Integer> translateForBatch(final T transformation,final Context context) {
        final Collection<Integer> transformationIds =
            translateForBatchInternal(transformation, context);

        return transformationIds;
    }

    protected abstract Collection<Integer> translateForBatchInternal(final T transformation,
                                                                     final Context context);

    @Override
    public Collection<Integer> translateForStreaming(final T transformation, final Context context) {
        final Collection<Integer> transformationIds=
            translateForStreamingInternal(transformation, context);
        return transformationIds;
    }

    protected abstract Collection<Integer> translateForStreamingInternal(final  T transformation,
                                                                         final Context context);
}
