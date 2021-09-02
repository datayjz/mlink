package com.mlink.functions.source;

public interface SourceFunction<T> {

    void run(SourceContext<T> ctx);

    void cancel();

    interface SourceContext<T> {
        void collect(T element);
    }
}
