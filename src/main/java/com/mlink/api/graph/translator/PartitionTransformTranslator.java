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
            //虚拟id是最后vertexId之上再重新递增的
            int virtualIds = Transformation.getNewNodeId();
            //添加分区虚拟节点
            streamGraph.addVirtualPartitionNode(
                inputId,
                virtualIds,
                transformation.getPartitioner(),
                transformation.getExchangeMode());
            virtualResult.add(virtualIds);
        }
        //因为是虚拟节点，不会创建实际的edge

        return virtualResult;
    }
}
