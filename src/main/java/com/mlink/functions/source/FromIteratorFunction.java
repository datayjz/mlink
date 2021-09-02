package com.mlink.functions.source;

import java.util.Iterator;

public class FromIteratorFunction<T> implements SourceFunction<T>{

    private final Iterator<T> iterator;

    private volatile boolean isRunning = true;

    public FromIteratorFunction(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public void run(SourceContext<T> ctx) {
        while (isRunning && iterator.hasNext()) {
            ctx.collect(iterator.next());
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
