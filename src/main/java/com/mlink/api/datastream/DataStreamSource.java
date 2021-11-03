package com.mlink.api.datastream;

import com.mlink.api.Boundedness;
import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.windowing.assigners.GlobalWindows;
import com.mlink.api.windowing.assigners.WindowAssigner;
import com.mlink.api.windowing.windows.GlobalWindow;
import com.mlink.api.windowing.windows.Window;
import com.mlink.connector.Source;
import com.mlink.api.operators.source.StreamSourceOperator;
import com.mlink.typeinfo.TypeInformation;

public class DataStreamSource<T, KEY> extends SingleOutputStreamOperator<T>{

    /**
     * 通过Source Operator来创建data stream
     */
    public DataStreamSource(StreamExecutionEnvironment environment,
                            StreamSourceOperator<OUT, ?> operator,
                            boolean isParallel,
                            String sourceName,
                            Boundedness boundedness) {

        super(environment, null);
    }

    public WindowedStream<T, KEY, GlobalWindow> countWindow(long size, long side) {
        return window(GlobalWindows.create())
    }

    public <W extends Window> WindowedStream<T, KEY, W> window(WindowAssigner<T, W> assigner) {
        return new WindowedStream<>(this, assigner);
    }
}
