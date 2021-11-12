package com.mlink.state.api;

import com.mlink.state.api.ttl.StateTtlConfig;
import com.mlink.typeinfo.TypeInformation;
import com.mlink.typeinfo.TypeSerializer;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 用于描述状态的基类，StateDescriptor专门用于创建State的分区状态
 * 所有子类都需要实现equal和hashCode，目的是用来做state区分
 */
public abstract class StateDescriptor<S extends State, T> implements Serializable {

    //创建state的唯一标识
    protected final String name;

    //state的value 类型
    private TypeInformation<T> typeInfo;

    private final AtomicReference<TypeSerializer<T>> serializerAtomicReference =
        new AtomicReference<>();

    //查询状态名称
    private String queryableStateName;

    //state ttl配置
    private StateTtlConfig ttlConfig = StateTtlConfig.DISABLED;

    //没设置value前的默认值
    protected transient T defaultValue;

    protected StateDescriptor(String name,
                              Class<T> type,
                              T defaultValue) {
        this.name = name;
        this.defaultValue =defaultValue;
    }

    protected StateDescriptor(String name, TypeInformation<T> typeInfo, T defaultValue) {
        this.name = name;
        this.typeInfo = typeInfo;
        this.defaultValue = defaultValue;
    }

    protected StateDescriptor(String name,
                              TypeSerializer<T> serializer,
                              T defaultValue) {
        this.name = name;
        this.serializerAtomicReference.set(serializer);
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        if (defaultValue != null) {
            return defaultValue;
        } else {
            return null;
        }
    }

    public TypeSerializer<T> getSerializer() {
        TypeSerializer<T> serializer = serializerAtomicReference.get();
        if (serializer != null) {
            return serializer;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * 设置Queryable state的查询名称
     */
    public void setQueryable(String queryableStateName) {
        if (this.queryableStateName == null) {
            this.queryableStateName = queryableStateName;
        } else {
            throw new IllegalStateException();
        }
    }

    public String getQueryableStateName() {
        return queryableStateName;
    }

    public boolean isQueryable() {
        return queryableStateName != null;
    }

    /**
     * 配置ttl
     */
    public void enableTimeToLive(StateTtlConfig ttlConfig) {
        this.ttlConfig = ttlConfig;
    }

    public StateTtlConfig getTtlConfig() {
        return ttlConfig;
    }

    public abstract Type getType();

    public enum Type {
        //value state
        VALUE,
        //list state
        LIST,
        //reduce state
        REDUCING,
        //aggregating state
        AGGREGATING,
        //map state
        MAP
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StateDescriptor<?, ?> that = (StateDescriptor<?, ?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + 31 * getClass().hashCode();
    }
}
