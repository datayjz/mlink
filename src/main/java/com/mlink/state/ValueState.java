package com.mlink.state;

public interface ValueState<T> {

    T value();
    void update(T value);
}
