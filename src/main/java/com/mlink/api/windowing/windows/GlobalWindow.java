package com.mlink.api.windowing.windows;

public class GlobalWindow extends Window {

    private static final GlobalWindow INSTANCE = new GlobalWindow();

    public static GlobalWindow get() {
        return INSTANCE;
    }

    @Override
    public long maxTimestamp() {
        return Long.MAX_VALUE;
    }
}
