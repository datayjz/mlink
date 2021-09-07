package com.mlink.api.datastream;

import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.functions.KeySelector;
import com.mlink.api.functions.sink.PrintSinkFunction;
import com.mlink.api.functions.sink.SinkFunction;
import com.mlink.api.functions.transformation.FilterFunction;
import com.mlink.api.functions.transformation.FlatMapFunction;
import com.mlink.api.operators.sink.StreamSinkOperator;
import com.mlink.api.operators.transformation.StreamFilterOperator;
import com.mlink.api.operators.transformation.StreamFlatMapOperator;
import com.mlink.api.transformation.OneInputTransformation;
import com.mlink.api.transformation.Transformation;
import com.mlink.api.functions.transformation.MapFunction;
import com.mlink.api.operators.OneInputStreamOperator;
import com.mlink.api.operators.transformation.StreamMapOperator;
import com.mlink.typeinfo.TypeInformation;
import java.util.ArrayList;
import java.util.List;

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
    //reduce需要在keyby后，所以在KeyedStream

    public <OUT> SingleOutputStreamOperator<OUT> map(MapFunction<IN, OUT> mapper) {
        //TODO get type
        TypeInformation<OUT> outType = null;
        return transform("Map", outType, new StreamMapOperator<>(mapper));
    }

    public <OUT> SingleOutputStreamOperator<OUT> flatMap(FlatMapFunction<IN, OUT> flatMapper) {
        //TODO get type
        TypeInformation<OUT> outType = null;
        return transform("Flat map", outType, new StreamFlatMapOperator<>(flatMapper));
    }

    public SingleOutputStreamOperator<IN> filter(FilterFunction<IN> filter) {
        return transform("Filter", null, new StreamFilterOperator<>(filter));
    }

    /**
     * 专门用于构建单流操作的Transformation
     */
    public <OUT> SingleOutputStreamOperator<OUT> transform(String operatorName,
                                                       TypeInformation<OUT> outTypeInfo,
                                                       OneInputStreamOperator<IN, OUT> operator) {

        OneInputTransformation<IN, OUT> transformation =
            new OneInputTransformation<>(this.transformation,
                operatorName,
                operator,
                outTypeInfo);

        return new SingleOutputStreamOperator<>(environment, transformation);
    }

    public <KEY> KeyedStream<IN, KEY> keyBy(KeySelector<IN, KEY> keySelector) {
        return new KeyedStream<>(this, keySelector);
    }

    //-------------------------------source op---------------------------//
    //source相关DataStream是通过StreamExecutionEnvironment来创建的DataStreamSource

    //-------------------------------sink op---------------------------//

    public DataStreamSink<IN> print() {
        PrintSinkFunction<IN> printSinkFunction = new PrintSinkFunction<>();
        return addSink(printSinkFunction);
    }

    public DataStreamSink<IN> addSink(SinkFunction<IN> sinkFunction) {
        StreamSinkOperator<IN> sinkOperator = new StreamSinkOperator<>(sinkFunction);
        DataStreamSink<IN> sink = new DataStreamSink<>(this, sinkOperator);
        return sink;
    }

    //-------------------------------多流合并---------------------------//

//    /**
//     * 多流合并，合并的data stream数据类型必须一致
//     */
//    public final DataStream<IN> union(DataStream<IN>... streams) {
//        //将指定流合并到一个union data stream中，该data stream的input就是这些待合并的data stream
//        List<Transformation<IN>> unionTransforms = new ArrayList<>();
//        //当前流的Transformation
//        unionTransforms.add(this.transformation);
//
//        for (DataStream<IN> stream : streams) {
//            if (!getType().equals(stream.getType())) {
//                throw new IllegalArgumentException();
//            }
//            unionTransforms.add(stream.getTransformation());
//        }
//
//        return new DataStream<>(this.environment, new UnionTransformation<>(unionTransforms));
//    }
//
//    /**
//     * 链接指定DataStream，两个数据流的数据类型可能不一致
//     */
//    public <OUT> ConnectedStream<IN, OUT> connect(DataStream<OUT> dataStream) {
//        return new ConnectedStream<>(this.environment, this, dataStream);
//    }
//
//    /**
//     * 链接指定BroadcastStream，链接后的数据流可以使用broadcast方法
//     */
//    public <R> BroadcastConnectedStream<IN, R> connect(BroadcastStream<R> broadcastStream) {
//        return new BroadcastConnectedStream<>(this.environment, this, broadcastStream);
//    }


    public StreamExecutionEnvironment getExecutionEnvironment() {
        return environment;
    }

    //当前data stream数据类型
    public TypeInformation<IN> getType() {
        //TODO
        return null;
    }

    public Transformation<IN> getTransformation() {
        return transformation;
    }
}
