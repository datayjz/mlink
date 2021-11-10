package com.mlink.api.graph.streamgraph.translator;

import com.mlink.api.operators.factory.SimpleOperatorFactory;
import com.mlink.api.operators.StreamGroupedReduceOperator;
import com.mlink.api.transformations.ReduceTransformation;
import java.util.Collection;

/**
 * 转换ReduceTransformation，在这里根据执行模式创建不同的reduce operator。
 *
 * batch: BatchGroupedReduceOperator
 * streaming: StreamingGroupedReduceOperator
 */
public class ReduceTransformationTranslator<IN, K>
    extends AbstractOneInputTransformationTranslator<IN,IN, ReduceTransformation<IN, K>> {

    @Override
    protected Collection<Integer> translateForBatchInternal(
        ReduceTransformation<IN, K> transformation, Context context) {
        return null;
    }

    @Override
    protected Collection<Integer> translateForStreamingInternal(
        ReduceTransformation<IN, K> transformation, Context context) {
        StreamGroupedReduceOperator<IN> reduceOperator =
            new StreamGroupedReduceOperator<>(transformation.getReducer());

        SimpleOperatorFactory<IN> operatorFactory = SimpleOperatorFactory.of(reduceOperator);
        return translateInternal(transformation, operatorFactory, context);
    }
}
