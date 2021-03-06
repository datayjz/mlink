package com.mlink.api.graph.streamgraph.translator;

import com.mlink.api.transformations.OneInputTransformation;
import java.util.Collection;

/**
 * 用于转换OneInputTransformation
 */
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
