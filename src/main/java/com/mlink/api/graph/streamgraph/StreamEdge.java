package com.mlink.api.graph.streamgraph;

import com.mlink.runtime.partitionner.StreamPartitioner;
import com.mlink.api.transformations.StreamExchangeMode;

public class StreamEdge {

    private final int sourceId;
    private final int targetId;

    private StreamExchangeMode exchangeMode;

    //输出分区策略
    private StreamPartitioner<?> outputPartitioner;

    public StreamEdge(StreamNode sourceNode,
                      StreamNode targetNode,
                      StreamPartitioner<?> outputPartitioner,
                      StreamExchangeMode exchangeMode) {

        this.sourceId = sourceNode.getId();
        this.targetId = targetNode.getId();
        this.outputPartitioner = outputPartitioner;
        this.exchangeMode = exchangeMode;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public StreamExchangeMode getExchangeMode() {
        return exchangeMode;
    }
}
