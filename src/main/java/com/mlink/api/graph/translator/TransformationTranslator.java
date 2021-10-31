package com.mlink.api.graph.translator;

import com.mlink.api.graph.StreamGraph;
import com.mlink.api.transformations.Transformation;
import java.util.Collection;

/**
 * 用以将Transformation转换为运行时实现，根据执行模式。
 */
public interface TransformationTranslator<OUT, T extends Transformation<OUT>> {

    Collection<Integer> translateForBatch(final T transformation, final Context context);

    Collection<Integer> translateForStreaming(final T transformation, final Context context);

    interface Context {

        StreamGraph getStreamGraph();

        Collection<Integer> getStreamNodeIds(Transformation<?> transformation);
    }
}
