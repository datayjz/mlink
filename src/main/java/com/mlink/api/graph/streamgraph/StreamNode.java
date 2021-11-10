package com.mlink.api.graph.streamgraph;

import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.runtime.tasks.StreamTask;
import java.util.ArrayList;
import java.util.List;

/**
 * StreamGraph中的点，对应一个具体的operator
 */
public class StreamNode {

    //唯一id，对应Transformation中自动递增id
    private final int id;
    //算子名称
    private String operatorName;

    private StreamOperatorFactory<?> operatorFactory;

    private int parallelism;
    private int maxParallelism;

    //通信buffer超时设置
    private long bufferTimeout;

    //该点对应的输入/输出边
    private List<StreamEdge> inputEdges = new ArrayList<>();
    private List<StreamEdge> outputEdges = new ArrayList<>();

    //该operator对应的执行task
    private final Class<? extends StreamTask> jobVertexClass;

    public StreamNode(Integer id,
                      String operatorName,
                      StreamOperatorFactory<?> operatorFactory,
                      Class<? extends StreamTask> jobVertexClass) {
        this.id = id;
        this.operatorName = operatorName;
        this.operatorFactory = operatorFactory;

        this.jobVertexClass = jobVertexClass;
    }

    public void addInputEdge(StreamEdge inputEdge) {
        if (inputEdge.getTargetId() != getId()) {
            throw new IllegalArgumentException();
        }
        inputEdges.add(inputEdge);
    }

    public void addOutputEdge(StreamEdge outputEdge) {
        if (outputEdge.getSourceId() != getId()) {
            throw new IllegalArgumentException();
        }
        outputEdges.add(outputEdge);
    }

    public List<StreamEdge> getInputEdges() {
        return inputEdges;
    }

    public List<StreamEdge> getOutputEdges() {
        return outputEdges;
    }

    public int getId() {
        return id;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public void setMaxParallelism(int maxParallelism) {
        this.maxParallelism = maxParallelism;
    }

    public int getParallelism() {
        return parallelism;
    }
}
