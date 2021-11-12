package com.mlink.state.api;

import com.mlink.typeinfo.TypeInformation;
import com.mlink.typeinfo.TypeSerializer;

public class ValueStateDescriptor<T> extends StateDescriptor<ValueState<T>, T>{

    public ValueStateDescriptor(String name, Class<T> typeClass) {
        super(name, typeClass, null);
    }

    public ValueStateDescriptor(String name, TypeInformation<T> typeInfo) {
        super(name, typeInfo, null);
    }

    public ValueStateDescriptor(String name, TypeSerializer<T> typeSerializer) {
        super(name, typeSerializer, null);
    }

    @Override
    public Type getType() {
        return Type.VALUE;
    }
}
