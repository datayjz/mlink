package com.mlink.api.datastream;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.transformation.Transformation;

/**
 * 单流操作
 * @param <T>
 */
public class SingleOutputStreamOperator<T> extends DataStream<T> {


    public SingleOutputStreamOperator(StreamExecutionEnvironment environment,
                                      Transformation<T> transformation) {
        super(environment, transformation);
    }
}
