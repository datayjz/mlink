package com.mlink.api.graph.translator;

import com.mlink.api.graph.StreamGraph;
import com.mlink.api.transformations.SideOutputTransformation;
import com.mlink.api.transformations.Transformation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SideOutputTransformationTranslator<OUT>
    extends SimpleTransformationTranslator<OUT, SideOutputTransformation<OUT>>{

    @Override
    protected Collection<Integer> translateForBatchInternal(
        SideOutputTransformation<OUT> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    @Override
    protected Collection<Integer> translateForStreamingInternal(
        SideOutputTransformation<OUT> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    private Collection<Integer> translateInternal(SideOutputTransformation<OUT> transformation,
                                                  Context context) {

        List<Integer> virtualResult = new ArrayList<>();
        StreamGraph streamGraph = context.getStreamGraph();
        Transformation<?> input = transformation.getInputs().get(0);

        for (int inputId : context.getStreamNodeIds(input)) {
            int virtualId = Transformation.getNewNodeId();
            streamGraph.addVirtualSideOutputNode(inputId, virtualId, transformation.getOutputTag());
            virtualResult.add(virtualId);
        }
        return virtualResult;
    }
}
