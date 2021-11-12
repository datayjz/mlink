package com.mlink.state.api;

import com.mlink.typeinfo.TypeInformation;
import com.mlink.typeinfo.TypeSerializer;
import java.util.List;

/**
 * 用于创建ListState的descriptor
 */
public class ListStateDescriptor<T> extends StateDescriptor<ListState<T>, List<T>> {

    public ListStateDescriptor(String name, Class<T> elementTypeClass) {
        super(name, new TypeInformation<>(), null);
    }

    public ListStateDescriptor(String name, TypeInformation<T> elementTypeInfo) {
        super(name, new TypeInformation<>(), null);
    }

    public ListStateDescriptor(String name, TypeSerializer<T> typeSerializer) {
        super(name, new TypeInformation<>(), null);
    }

    @Override
    public Type getType() {
        return Type.LIST;
    }
}
