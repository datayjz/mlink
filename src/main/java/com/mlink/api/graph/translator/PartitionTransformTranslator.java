package com.mlink.api.graph.translator;

import com.mlink.api.graph.StreamGraph;
import com.mlink.api.transformations.PartitionTransformation;
import com.mlink.api.transformations.Transformation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PartitionTransformTranslator<IN>
    extends SimpleTransformationTranslator<IN, PartitionTransformation<IN>>{

    @Override
    protected Collection<Integer> translateForBatchInternal(
        PartitionTransformation<IN> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    @Override
    protected Collection<Integer> translateForStreamingInternal(
        PartitionTransformation<IN> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    private Collection<Integer> translateInternal(PartitionTransformation<IN> transformation, Context context) {
        StreamGraph streamGraph = context.getStreamGraph();
        Transformation<?> input = transformation.getInputs().get(0);

        List<Integer> virtualResult = new ArrayList<>();
        for (int inputId : context.getStreamNodeIds(input)) {
            int virtualIds = Transformation.getNewNodeId();
            streamGraph.addVirtualPartitionNode(
                inputId,
                virtualIds,
                transformation.getPartitioner(),
                transformation.getExchangeMode());
            virtualResult.add(virtualIds);
        }
        return virtualResult;
    }
}
