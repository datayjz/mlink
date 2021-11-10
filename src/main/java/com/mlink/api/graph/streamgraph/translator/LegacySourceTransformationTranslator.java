package com.mlink.api.graph.streamgraph.translator;

import com.mlink.api.graph.streamgraph.StreamGraph;
import com.mlink.api.transformations.LegacySourceTransformation;
import java.util.Collection;
import java.util.Collections;

public class LegacySourceTransformationTranslator<OUT>
    extends SimpleTransformationTranslator<OUT, LegacySourceTransformation<OUT>>{

    //流批处理方式一致
    @Override
    protected Collection<Integer> translateForBatchInternal(
        LegacySourceTransformation<OUT> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    @Override
    protected Collection<Integer> translateForStreamingInternal(
        LegacySourceTransformation<OUT> transformation, Context context) {
        return translateInternal(transformation, context);
    }

    /**
     * 实际转换方法，将Transformation翻译为StreamNode
     */
    private Collection<Integer> translateInternal(
        final LegacySourceTransformation<OUT> transformation, final Context context) {

        StreamGraph streamGraph = context.getStreamGraph();
        int transformationId = transformation.getId();

        //将当前source添加到StreamGraph中，会创建一个StreamNode
        streamGraph.addLegacySource(
            transformationId,
            transformation.getOperatorFactory(),
            transformation.getName());

        //将并发信息设置到StreamNode中
        streamGraph.setParallelism(transformationId, transformation.getParallelism());
        streamGraph.setMaxParallelism(transformationId, transformation.getMaxParallelism());

        //因为是source节点，没有上游节点了，所以不需要创建edge

        return Collections.singleton(transformationId);
    }
}
