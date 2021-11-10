package com.mlink.api.graph.streamgraph;

import com.mlink.api.graph.streamgraph.translator.LegacySinkTransformationTranslator;
import com.mlink.api.graph.streamgraph.translator.LegacySourceTransformationTranslator;
import com.mlink.api.graph.streamgraph.translator.OneInputTransformationTranslator;
import com.mlink.api.graph.streamgraph.translator.PartitionTransformTranslator;
import com.mlink.api.graph.streamgraph.translator.ReduceTransformationTranslator;
import com.mlink.api.graph.streamgraph.translator.TransformationTranslator;
import com.mlink.api.transformations.LegacySinkTransformation;
import com.mlink.api.transformations.LegacySourceTransformation;
import com.mlink.api.transformations.OneInputTransformation;
import com.mlink.api.transformations.PartitionTransformation;
import com.mlink.api.transformations.ReduceTransformation;
import com.mlink.api.transformations.Transformation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据Transformation信息来生成StreamGraph
 */
public class StreamGraphGenerator {

    private List<Transformation<?>> transformations;

    private StreamGraph streamGraph;

    private Map<Transformation<?>, Collection<Integer>> alreadyTransformed;

    private static Map<
        Class<? extends Transformation>,
        TransformationTranslator<?, ? extends Transformation>> translatorMap;

    static {
        Map<Class<? extends Transformation>, TransformationTranslator<?, ? extends Transformation>>
            tmp = new HashMap<>();
        tmp.put(LegacySourceTransformation.class, new LegacySourceTransformationTranslator<>());
        tmp.put(OneInputTransformation.class, new OneInputTransformationTranslator<>());
        tmp.put(PartitionTransformation.class, new PartitionTransformTranslator<>());
        tmp.put(ReduceTransformation.class, new ReduceTransformationTranslator<>());
        tmp.put(LegacySinkTransformation.class, new LegacySinkTransformationTranslator<>());

        translatorMap = new HashMap<>(tmp);
    }

    public StreamGraphGenerator(List<Transformation<?>> transformations) {
        this.transformations = transformations;
    }

    public StreamGraph generate() {
        streamGraph = new StreamGraph();

        alreadyTransformed = new HashMap<>();

        for (Transformation<?> transformation : transformations) {
            transform(transformation);
        }

        final StreamGraph buildGraph = streamGraph;
        alreadyTransformed.clear();
        alreadyTransformed = null;
        streamGraph = null;

        return buildGraph;
    }

    private Collection<Integer> transform(Transformation<?> transform) {
        //是否已经转换
        if (alreadyTransformed.containsKey(transform)) {
            return alreadyTransformed.get(transform);
        }

        //找到对应的Translator
        TransformationTranslator<?, Transformation<?>> translator =
            (TransformationTranslator<?, Transformation<?>>) translatorMap.get(transform.getClass());

        Collection<Integer> transformedIds = null;
        if (translator != null) {
            transformedIds = translate(translator, transform);
        }
        if (!alreadyTransformed.containsKey(transform)) {
            alreadyTransformed.put(transform, transformedIds);
        }
        return transformedIds;
    }

    private Collection<Integer> translate(TransformationTranslator<?, Transformation<?>> translator,
                                          Transformation<?> transformation) {

        //递归转换该节点的父节点
        List<Collection<Integer>> allInputIds = new ArrayList<>();
        List<Transformation<?>> parentTransforms = transformation.getInputs();
        for (Transformation<?> transform : parentTransforms) {
            allInputIds.add(transform(transform));
        }

        if (alreadyTransformed.containsKey(transformation)) {
            return alreadyTransformed.get(transformation);
        }

        //StreamGraph传递给Context，各个Translator向这个graph里面添加vertext和edge
        ContextImp contextImp = new ContextImp(streamGraph);
        return translator.translateForStreaming(transformation, contextImp);
    }

    private static class ContextImp implements TransformationTranslator.Context {

        private final StreamGraph streamGraph;

        public ContextImp(StreamGraph streamGraph) {
            this.streamGraph = streamGraph;
        }

        @Override
        public StreamGraph getStreamGraph() {
            return streamGraph;
        }

        @Override
        public Collection<Integer> getStreamNodeIds(Transformation<?> transformation) {
            return null;
        }
    }
}
