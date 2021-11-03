package com.mlink.util;

import java.io.Serializable;

public abstract class Tuple implements Serializable {

    public abstract <T> T getField(int pos);

    public abstract <T> void setField(T value, int pos);
}
