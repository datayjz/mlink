package com.mlink.execution;

import com.mlink.configuration.Configuration;

public interface PipelineExecutorFactory {

    PipelineExecutor getExecutor(final Configuration configuration);
}
