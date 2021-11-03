package com.mlink.api.graph.translator;

import com.mlink.api.graph.StreamGraph;
import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.api.transformations.Transformation;
import java.util.Collection;
import java.util.Collections;

/**
 * 单边输入转换Transformation的基类实现，提供了公共实现：
 * 1. 添加节点，根据输入
 * 2. 根据输入和当前节点创建边
 * 3. 将边赋值给输入和当前节点
 */
public abstract class AbstractOneInputTransformationTranslator<IN, OUT, OP extends Transformation<OUT>>
    extends SimpleTransformationTranslator<OUT, OP> {

    protected Collection<Integer> translateInternal(final Transformation<OUT> transformation,
                                                    final StreamOperatorFactory<OUT> operatorFactory,
                                                    final Context context) {

        StreamGraph streamGraph = context.getStreamGraph();
        int transformationId = transformation.getId();
        Transformation<?> inputTransformation = transformation.getInputs().get(0);

        //创建StreamNode
        streamGraph.addOperator(
            transformationId,
            operatorFactory,
            transformation.getName());

        //将并发度配置到StreamNode中
        streamGraph.setParallelism(transformationId, transformation.getParallelism());
        streamGraph.setMaxParallelism(transformationId, transformation.getMaxParallelism());

        //对上游input和当前transformation建立edge
        for (Integer inputId : context.getStreamNodeIds(inputTransformation)) {
            streamGraph.addEdge(inputId, transformationId);
        }

        return Collections.singleton(transformationId);
    }
}
