package com.mlink.api.common;

import java.io.Serializable;

public class OutputTag<T> implements Serializable {

    private final String id;

    public OutputTag(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
