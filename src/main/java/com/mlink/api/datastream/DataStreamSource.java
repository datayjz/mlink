package com.mlink.api.datastream;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.connector.Source;
import com.mlink.operator.StreamSourceOperator;
import com.mlink.typeinfo.TypeInformation;

public class DataStreamSource<T> extends DataStream<T>{

    /**
     * 通过Source Operator来创建data stream
     */
    public DataStreamSource(StreamExecutionEnvironment environment,
                            TypeInformation<T> outTypeInfo,
                            StreamSourceOperator<?> operator,
                            String sourceName) {

    }

    /**
     * 通过Source connector来创建data stream
     */
    public DataStreamSource(StreamExecutionEnvironment environment,
                            Source<T> source,
                            TypeInformation<T> outTypeInfo,
                            String sourceName) {

    }
}
