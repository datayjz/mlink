package com.mlink.api.graph.translator;

import com.mlink.api.transformations.OneInputTransformation;
import java.util.Collection;

public class OneInputTransformationTranslator<IN, OUT>
    extends  AbstractOneInputTransformationTranslator<IN, OUT, OneInputTransformation<IN, OUT>>{

    @Override
    protected Collection<Integer> translateForBatchInternal(
        OneInputTransformation<IN, OUT> transformation, Context context) {
        return translateInternal(transformation, transformation.getOperatorFactory(), context);
    }

    @Override
    protected Collection<Integer> translateForStreamingInternal(
        OneInputTransformation<IN, OUT> transformation, Context context) {
        return translateInternal(transformation, transformation.getOperatorFactory(), context);
    }
}
