package com.mlink.api.datastream;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.operators.StreamSource;
import com.mlink.api.transformations.LegacySourceTransformation;

public class DataStreamSource<T> extends SingleOutputStreamOperator<T>{

    /**
     * 创建Source stream，内部创建LegacySourceTransformation
     */
    public DataStreamSource(StreamExecutionEnvironment environment,
                            StreamSource<T, ?> operator,
                            String sourceName) {
        super(environment,
            new LegacySourceTransformation<>(sourceName, environment.getParallelism(), operator));
    }

}
