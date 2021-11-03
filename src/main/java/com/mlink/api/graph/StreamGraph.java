package com.mlink.api.graph;

import com.mlink.api.common.ExecutionConfig;
import com.mlink.api.common.OutputTag;
import com.mlink.api.operators.factory.StreamOperatorFactory;
import com.mlink.api.transformations.StreamExchangeMode;
import com.mlink.runtime.partitioner.ForwardPartitioner;
import com.mlink.runtime.partitioner.RebalancePartitioner;
import com.mlink.runtime.partitioner.StreamPartitioner;
import com.mlink.runtime.tasks.OneInputStreamTask;
import com.mlink.runtime.tasks.SourceStreamTask;
import com.mlink.util.Tuple2;
import com.mlink.util.Tuple3;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对应流任务的执行拓扑，包括了构建JobGraph的所有信息。主要由vertex StreamNode 和edge StreamEdge组成。
 * StreamNode包括了：执行operator、对应的输入和输出edge以及operator所对应的实际执行task class等信息
 * StreamEdge包括了：上下游StreamNode(使用id标识)、partitioner以及数据交换方式StreamExchangeMode。
 */
public class StreamGraph {

    private String jobName;

    //dag所有节点，vertexId -> vertex
    private Map<Integer, StreamNode> streamNodes;
    private Set<Integer> sources;
    private Set<Integer> sinks;
    //记录虚拟节点信息，<虚拟id, <虚拟算子上游id, 分区器, 数据交换方式>>
    private Map<Integer, Tuple3<Integer, StreamPartitioner<?>, StreamExchangeMode>> virtualPartitionNodes;
    private Map<Integer, Tuple2<Integer, OutputTag<?>>> virtualSideOutputNodes;

    public StreamGraph() {

    }

    public void clear() {
        streamNodes = new HashMap<>();
        virtualPartitionNodes = new HashMap<>();
        virtualSideOutputNodes = new HashMap<>();
        sources = new HashSet<>();
        sinks = new HashSet<>();
    }

    /**
     * 添加source 算子
     */
    public <IN, OUT> void addLegacySource(Integer vertexId,
                                          StreamOperatorFactory<OUT> operatorFactory,
                                          String operatorName) {

        addOperator(vertexId, operatorFactory, operatorName);
        sources.add(vertexId);
    }

    /**
     * 添加sink算子
     */
    public <IN, OUT> void addSink(Integer vertexID,
                                  StreamOperatorFactory<OUT> operatorFactory,
                                  String operatorName) {
        addOperator(vertexID, operatorFactory, operatorName);
        sinks.add(vertexID);
    }

    /**
     * 统一添加算子
     */
    public  <IN, OUT> void addOperator(Integer vertexID,
                                       StreamOperatorFactory<OUT> operatorFactory,
                                       String operatorName) {
        Class<?> invokableClass = operatorFactory.isStreamSource() ?
                                  SourceStreamTask.class :
                                  OneInputStreamTask.class;

        addNode(vertexID, operatorFactory, operatorName, invokableClass);
    }


    /**
     * 将算子转换为StreamGraph中的vertex,不对外暴露，由具体operator决定
     */
    private StreamNode addNode(int vertexId,
                               StreamOperatorFactory<?> operatorFactory,
                               String operatorName,
                               Class<?> invokableClass) {
        StreamNode vertex = new StreamNode(vertexId, operatorName, operatorFactory, invokableClass);
        streamNodes.put(vertexId, vertex);
        return vertex;
    }

    public StreamNode getStreamNode(int vertexId) {
        return streamNodes.get(vertexId);
    }

    /**
     * 添加虚拟Side output节点
     */
    public void addVirtualSideOutputNode(Integer originalId,
                                         Integer virtualId,
                                         OutputTag<?> outputTag) {
        if (virtualSideOutputNodes.containsKey(virtualId)) {
            throw new IllegalArgumentException();
        }

        virtualSideOutputNodes.put(virtualId, new Tuple2<>(originalId, outputTag));
    }
    /**
     * 添加分区虚拟节点(分区只会影响上下游连接方式)
     */
    public void addVirtualPartitionNode(Integer originalId,//虚拟节点的上游节点
                                        Integer virtualId,
                                        StreamPartitioner<?> partitioner,
                                        StreamExchangeMode exchangeMode) {
        virtualPartitionNodes.put(virtualId, new Tuple3<>(originalId, partitioner, exchangeMode));
    }

    public void addEdge(int upStreamVertexId, int downStreamVertexId) {
        addEdgeInternal(upStreamVertexId, downStreamVertexId, null, null);
    }

    private void addEdgeInternal(int upStreamVertexId,
                                 int downStreamVertexId,
                                 StreamPartitioner<?> partitioner,
                                 StreamExchangeMode exchangeMode) {

        //如果上游是virtual节点，需要继续向上找到具体物理节点
        if (virtualPartitionNodes.containsKey(upStreamVertexId)) {
            int virtualId = upStreamVertexId;
            //虚拟节点上游节点
            upStreamVertexId = virtualPartitionNodes.get(virtualId).f0;
            if (partitioner == null) {
                partitioner = virtualPartitionNodes.get(virtualId).f1;
            }
            exchangeMode = virtualPartitionNodes.get(virtualId).f2;

            //可能还是虚拟节点，所以递归迭代向上找，直到周到物理节点
            addEdgeInternal(upStreamVertexId, downStreamVertexId, partitioner, exchangeMode);
        } else {
            StreamNode upStreamNode = streamNodes.get(upStreamVertexId);
            StreamNode downStreamNode = streamNodes.get(downStreamVertexId);

            //如果没有指定并发度，当上下游并发度一致时，使用forward partitioner尽量来本地数据传输计算。如果不一致则使用rebalance
            // partitioner来round-robin发送
            if (partitioner == null && upStreamNode.getParallelism() == downStreamNode.getParallelism()) {
                partitioner = new ForwardPartitioner<>();
            } else {
                partitioner = new RebalancePartitioner<>();
            }

            if (exchangeMode == null) {
                exchangeMode = StreamExchangeMode.UNDEFINED;
            }

            StreamEdge edge = new StreamEdge(upStreamNode, downStreamNode, partitioner, exchangeMode);

            getStreamNode(edge.getSourceId()).addOutputEdge(edge);
            getStreamNode(edge.getTargetId()).addInputEdge(edge);
        }
    }

    public void setParallelism(int vertexId, int parallelism) {
        if (getStreamNode(vertexId) != null) {
            getStreamNode(vertexId).setParallelism(parallelism);
        }
    }

    public void setMaxParallelism(int vertexId, int maxParallelism) {
        if (getStreamNode(vertexId) != null) {
            getStreamNode(vertexId).setMaxParallelism(maxParallelism);
        }
    }

}
