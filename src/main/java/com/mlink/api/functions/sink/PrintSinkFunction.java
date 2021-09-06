package com.mlink.api.functions.sink;

import com.mlink.api.functions.context.StreamingRuntimeContext;
import com.mlink.core.configuration.Configuration;
import java.io.PrintStream;

public class PrintSinkFunction<IN> extends SinkRichFunction<IN>{

    private transient PrintStream printStream;
    private transient String prefix;

    public PrintSinkFunction() {

    }

    @Override
    public void open(Configuration parameters) throws Exception {
        StreamingRuntimeContext context = (StreamingRuntimeContext) getRuntimeContext();
        printStream = System.out;
        int indexOfThisSubtask = context.getIndexOfThisSubtask();
        int numberOfParallelSubtasks = context.getNumberOfParallelSubtasks();
        if (numberOfParallelSubtasks > 1) {
            if (prefix.isEmpty()) {
                prefix += ":";
            }
            prefix += (indexOfThisSubtask);
        }
        if (!prefix.isEmpty()) {
            prefix += "> ";
        }
    }

    @Override
    public void invoke(IN value, Context context) throws Exception {
        printStream.println(prefix + value.toString());
    }
}
