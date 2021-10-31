package com.mlink.api.datastream;

import com.mlink.api.common.OutputTag;
import com.mlink.api.environment.StreamExecutionEnvironment;
import com.mlink.api.transformations.SideOutputTransformation;
import com.mlink.api.transformations.Transformation;
import com.mlink.typeinfo.TypeInformation;
import java.util.HashMap;
import java.util.Map;

/**
 * 单流操作
 * @param <T>
 */
public class SingleOutputStreamOperator<T> extends DataStream<T> {

    private Map<OutputTag<?>, TypeInformation<?>> requestedSideOutputs = new HashMap<>();

    public SingleOutputStreamOperator(StreamExecutionEnvironment environment,
                                      Transformation<T> transformation) {
        super(environment, transformation);
    }


    public <X> DataStream<X> getSideOutput(OutputTag<X> sideOutputTag) {
        sideOutputTag = new OutputTag<>(sideOutputTag.getId());

        TypeInformation<?> type = requestedSideOutputs.get(sideOutputTag);

        //之前注册的类型和当前output的类型不一致。主要用于校验传递进来的ouput tag类型
        if (type != null && type.equals(sideOutputTag.getTypeInfo())) {
            throw new UnsupportedOperationException();
        }

        requestedSideOutputs.put(sideOutputTag, sideOutputTag.getTypeInfo());

        SideOutputTransformation<X> sideOutputTransformation =
            new SideOutputTransformation<>(this.getTransformation(), sideOutputTag);
        return new DataStream<>(this.getExecutionEnvironment(), sideOutputTransformation);
    }
}
