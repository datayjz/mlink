package com.mlink.client;

import com.mlink.api.graph.streamgraph.Pipeline;
import com.mlink.configuration.Configuration;
import com.mlink.runtime.jobgraph.JobGraph;

/**
 * 用于将DataStream API的StreamGraph转化为JobGraph
 */
public class StreamGraphTranslator implements FlinkPipelineTranslator{

    @Override
    public JobGraph translateToJobGraph(Pipeline pipeline, Configuration configuration,
                                        int defaultParallelism) {
        return null;
    }

    @Override
    public String translateToJSONExecutionPlan(Pipeline pipeline) {
        return null;
    }
}
