package com.mlink.api.common;

import com.mlink.typeinfo.TypeInformation;
import java.io.Serializable;
import java.util.Objects;

public class OutputTag<T> implements Serializable {

    private final String id;

    private final TypeInformation<T> typeInfo;


    public OutputTag(String id) {
        this.id = id;
        //TODO type system extract
        this.typeInfo = null;
    }

    public OutputTag(String id, TypeInformation<T> typeInfo) {
        this.id = id;
        this.typeInfo = typeInfo;
    }

    public String getId() {
        return id;
    }

    public TypeInformation<T> getTypeInfo() {
        return typeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OutputTag<?> outputTag = (OutputTag<?>) o;
        return Objects.equals(id, outputTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
