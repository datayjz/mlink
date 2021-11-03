package com.mlink.runtime.streamrecord;

public class StreamRecord<T> {

    private T value;

    /**
     * 记录所对应的时间戳
     */
    private long timestamp;
    /**
     * 用于标识时间戳是否设置
     */
    private boolean hasTimestamp;

    public StreamRecord(T value) {
        this.value = value;
    }

    public StreamRecord(T value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
        this.hasTimestamp = true;
    }

    public T getValue() {
        return value;
    }

    public long getTimestamp() {
        //如果设置了时间戳直接返回，否则返回Long最小值，表示没有设置
        if (hasTimestamp) {
            return timestamp;
        } else {
            return Long.MIN_VALUE;
        }
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        this.hasTimestamp = true;
    }

    public boolean hasTimestamp() {
        return hasTimestamp;
    }


    public <X> StreamRecord<X> replace(X element) {
        this.value = (T) element;
        return (StreamRecord<X>) this;
    }
}
