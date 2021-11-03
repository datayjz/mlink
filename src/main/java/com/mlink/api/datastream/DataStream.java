package com.mlink.api.datastream;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.functions.KeySelector;
import com.mlink.api.functions.SinkFunction;
import com.mlink.api.functions.FlatMapFunction;
import com.mlink.api.operators.StreamSink;
import com.mlink.api.operators.StreamFlatMap;
import com.mlink.api.transformations.OneInputTransformation;
import com.mlink.api.transformations.Transformation;
import com.mlink.api.functions.MapFunction;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.api.operators.StreamMap;

/**
 * DataStream API是Flink编写streaming任务的核心API，同时也是SQL和Table API的底层核心支撑。
 * DataStream API主要
 */
public class DataStream<IN> {

    protected final StreamExecutionEnvironment environment;
    protected final Transformation<IN> transformation;

    public DataStream(StreamExecutionEnvironment environment,
                      Transformation<IN> transformation) {

        this.environment = environment;
        this.transformation = transformation;
    }
    //-------------------------------单流处理---------------------------//

    public <OUT> SingleOutputStreamOperator<OUT> map(MapFunction<IN, OUT> mapper) {
        //创建map operator给transformation
        StreamMap<IN, OUT> streamMapOperator = new StreamMap<>(mapper);
        return transform("Map", streamMapOperator);
    }

    public <OUT> SingleOutputStreamOperator<OUT> flatMap(FlatMapFunction<IN, OUT> flatMapper) {
        //创建flatmap operator 给transform
        StreamFlatMap<IN, OUT> streamFlatMapOperator = new StreamFlatMap<>(flatMapper);
        return transform("Flat map", new StreamFlatMap<>(flatMapper));
    }

    public <KEY> KeyedStream<IN, KEY> keyBy(KeySelector<IN, KEY> keySelector) {
        return new KeyedStream<>(this, keySelector);
    }

    //reduce是作用在keyed data stream之上的

    /**
     * 专门用于构建单流操作的Transformation
     */
    public <OUT> SingleOutputStreamOperator<OUT> transform(String operatorName,
                                                           OneInputStreamOperator<IN, OUT> operator) {

        OneInputTransformation<IN, OUT> transformation =
            new OneInputTransformation<>(
                operatorName,
                environment.getParallelism(),  //注意这里默认使用StreamExecutionEnvironment中的并发度，需要显示设置
                operator,
                this.transformation);

        return new SingleOutputStreamOperator<>(environment, transformation);
    }

    public DataStreamSink<IN> addSink(SinkFunction<IN> sinkFunction) {
        //创建SinkOperator 给DataStreamSink，DataStreamSink在给SinkTransformation
        StreamSink<IN> sinkOperator = new StreamSink<>(sinkFunction);
        DataStreamSink<IN> sink = new DataStreamSink<>(this, sinkOperator);
        return sink;
    }


    public StreamExecutionEnvironment getExecutionEnvironment() {
        return environment;
    }


    public Transformation<IN> getTransformation() {
        return transformation;
    }
}
