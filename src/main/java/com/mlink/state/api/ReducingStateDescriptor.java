package com.mlink.state.api;

import com.mlink.api.functions.ReduceFunction;
import com.mlink.typeinfo.TypeInformation;
import com.mlink.typeinfo.TypeSerializer;

public class ReducingStateDescriptor<T> extends StateDescriptor<ReducingState<T>, T>{

    private final ReduceFunction<T> reduceFunction;

    public ReducingStateDescriptor(String name,
                                   ReduceFunction<T> reduceFunction,
                                   Class<T> typeClass) {
        super(name, typeClass, null);
        this.reduceFunction = reduceFunction;
    }

    public ReducingStateDescriptor(String name,
                                   ReduceFunction<T> reduceFunction,
                                   TypeInformation<T> typeInfo) {
        super(name, typeInfo, null);
        this.reduceFunction = reduceFunction;
    }

    public ReducingStateDescriptor(String name,
                                   ReduceFunction<T> reduceFunction,
                                   TypeSerializer<T> typeSerializer) {
        super(name, typeSerializer, null);
        this.reduceFunction = reduceFunction;
    }

    public ReduceFunction<T> getReduceFunction() {
        return reduceFunction;
    }

    @Override
    public Type getType() {
        return Type.REDUCING;
    }
}
