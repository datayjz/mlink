package com.mlink.api.graph.translator;

import com.mlink.api.graph.StreamGraph;
import com.mlink.api.transformations.LegacySinkTransformation;
import com.mlink.api.transformations.Transformation;
import java.util.Collection;
import java.util.Collections;

public class LegacySinkTransformationTranslator<IN>
    extends SimpleTransformationTranslator<Object, LegacySinkTransformation<IN>> {

    @Override
    protected Collection<Integer> translateForBatchInternal(
        LegacySinkTransformation<IN> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    @Override
    protected Collection<Integer> translateForStreamingInternal(
        LegacySinkTransformation<IN> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    private Collection<Integer> translateInternal(final LegacySinkTransformation<IN> transformation,
                                                  final Context context) {
        StreamGraph streamGraph = context.getStreamGraph();

        int sinkId = transformation.getId();
        //sink都是单流输入
        Transformation<?> input = transformation.getInputs().get(0);

        streamGraph.addSink(
            transformation.getId(),
            transformation.getOperatorFactory(),
            transformation.getName());

        streamGraph.setParallelism(sinkId, transformation.getParallelism());
        streamGraph.setMaxParallelism(sinkId, transformation.getMaxParallelism());

        //创建edge
        for (Integer inputId : context.getStreamNodeIds(transformation)) {
            streamGraph.addEdge(inputId, sinkId);
        }

        return Collections.emptyList();
    }
}
