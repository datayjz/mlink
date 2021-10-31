package com.mlink.api.graph.translator;

import com.mlink.api.transformations.Transformation;
import java.util.Collection;

/**
 * 所有TransformationTranslator的基类
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
