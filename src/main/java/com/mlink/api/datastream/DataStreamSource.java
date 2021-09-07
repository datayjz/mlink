package com.mlink.api.datastream;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.connector.Source;
import com.mlink.api.operators.source.StreamSourceOperator;
import com.mlink.typeinfo.TypeInformation;

public class DataStreamSource<OUT> extends SingleOutputStreamOperator<OUT>{

    /**
     * 通过Source Operator来创建data stream
     */
    public DataStreamSource(StreamExecutionEnvironment environment,
                            StreamSourceOperator<OUT, ?> operator,
                            TypeInformation<OUT> outTypeInfo,
                            String sourceName) {

        super(environment, null);
    }

    /**
     * 通过Source connector来创建data stream
     */
    public DataStreamSource(StreamExecutionEnvironment environment,
                            Source<OUT> source,
                            TypeInformation<OUT> outTypeInfo,
                            String sourceName) {

        super(environment, null);
    }
}
