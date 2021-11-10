package com.mlink.api.graph.streamgraph.translator;

import com.mlink.api.graph.streamgraph.StreamGraph;
import com.mlink.api.transformations.Transformation;
import java.util.Collection;

/**
 * 用以将Transformation转换为运行时实现，根据执行模式。
 */
public interface TransformationTranslator<OUT, T extends Transformation<OUT>> {

    /**
     * 将传递的Transformation转换为能够支持batch-style模式执行的实现
     */
    Collection<Integer> translateForBatch(final T transformation, final Context context);

    /**
     * 将传递的Transformation转换为能够支持streaming-style模式执行的实现
     * @param transformation
     * @param context
     * @return
     */
    Collection<Integer> translateForStreaming(final T transformation, final Context context);

    /**
     * 为转换提供必要的上下文信息
     */
    interface Context {

        /**
         * 返回正在转换的StreamGraph
         */
        StreamGraph getStreamGraph();

        /**
         * 返回传递的Transformation对应的id列表
         */
        Collection<Integer> getStreamNodeIds(Transformation<?> transformation);
    }
}
