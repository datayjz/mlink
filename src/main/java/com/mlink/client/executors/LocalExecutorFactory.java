package com.mlink.client.executors;

import com.mlink.configuration.Configuration;
import com.mlink.execution.PipelineExecutor;
import com.mlink.execution.PipelineExecutorFactory;

public class LocalExecutorFactory implements PipelineExecutorFactory {

    @Override
    public PipelineExecutor getExecutor(Configuration configuration) {
        return null;
    }
}
