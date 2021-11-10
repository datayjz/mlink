package com.mlink.client;

import com.mlink.api.graph.streamgraph.Pipeline;
import com.mlink.configuration.Configuration;
import com.mlink.runtime.jobgraph.JobGraph;

/**
 * 用于将Pipeline(StreamGraph或Plan)转换为JobGraph
 */
public interface FlinkPipelineTranslator {

    /**
     * 从给定的Pipeline创建一个JobGraph，并将给定的jar文件和路径添加到JobGraph
     */
    JobGraph translateToJobGraph(Pipeline pipeline, Configuration configuration, int defaultParallelism);

    /**
     * 从Pipeline提取json格式的执行计划
     */
    String translateToJSONExecutionPlan(Pipeline pipeline);
}
