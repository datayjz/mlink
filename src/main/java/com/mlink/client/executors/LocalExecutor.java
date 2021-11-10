package com.mlink.client.executors;

import com.mlink.api.graph.streamgraph.Pipeline;
import com.mlink.configuration.Configuration;
import com.mlink.execution.JobClient;
import com.mlink.execution.PipelineExecutor;
import java.util.concurrent.CompletableFuture;

public class LocalExecutor implements PipelineExecutor {

    private final Configuration configuration;

    private LocalExecutor(Configuration configuration) {
        this.configuration = configuration;

    }

    public
    static LocalExecutor create(Configuration configuration) {
        return new LocalExecutor(configuration);
    }

    @Override
    public CompletableFuture<JobClient> execute(Pipeline pipeline, Configuration configuration)
        throws Exception {


        return null;
    }
}
