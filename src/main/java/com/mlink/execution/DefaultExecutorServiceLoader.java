package com.mlink.execution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * PipelineExecutorServiceLoader的默认实现，会根据service loader来找PipelineExecutorFactory的实现
 */
public class DefaultExecutorServiceLoader implements PipelineExecutorServiceLoader{

    @Override
    public PipelineExecutorFactory getExecutorFactory() throws Exception {
        ServiceLoader<PipelineExecutorFactory> loader =
            ServiceLoader.load(PipelineExecutorFactory.class);

        Iterator<PipelineExecutorFactory> factories = loader.iterator();
        List<PipelineExecutorFactory> factoryList = new ArrayList<>();
        while (factories.hasNext()) {
            factoryList.add(factories.next());
        }

        //只能有且只有一个，否则异常
        if (factoryList.size() != 1) {
            throw new IllegalStateException();
        }

        return factoryList.get(0);
    }
}
