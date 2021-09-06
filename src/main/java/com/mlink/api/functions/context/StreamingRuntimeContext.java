package com.mlink.api.functions.context;

public class StreamingRuntimeContext implements RuntimeContext{

    @Override
    public int getNumberOfParallelSubtasks() {
        return 0;
    }

    @Override
    public int getMaxNumberOfParallelSubtasks() {
        return 0;
    }

    @Override
    public int getIndexOfThisSubtask() {
        return 0;
    }
}
