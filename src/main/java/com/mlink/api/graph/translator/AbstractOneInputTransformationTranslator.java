package com.mlink.api.graph.translator;

import com.mlink.api.graph.StreamGraph;
import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.api.transformations.Transformation;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractOneInputTransformationTranslator<IN, OUT, OP extends Transformation<OUT>>
    extends SimpleTransformationTranslator<OUT, OP> {

    protected Collection<Integer> translateInternal(final Transformation<OUT> transformation,
                                                    final StreamOperatorFactory<OUT> operatorFactory,
                                                    final Context context) {

        StreamGraph streamGraph = context.getStreamGraph();
        int transformationId = transformation.getId();
        Transformation<?> inputTransformation = transformation.getInputs().get(0);

        streamGraph.addOperator(
            transformationId,
            operatorFactory,
            transformation.getName());

        streamGraph.setParallelism(transformationId, transformation.getParallelism());
        streamGraph.setMaxParallelism(transformationId, transformation.getMaxParallelism());

        for (Integer inputId : context.getStreamNodeIds(inputTransformation)) {
            streamGraph.addEdge(inputId, transformationId);
        }

        return Collections.singleton(transformationId);
    }
}
