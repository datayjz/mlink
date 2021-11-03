package com.mlink.util;

public class Tuple3<T0, T1, T2> extends Tuple {

    public T0 f0;

    public T1 f1;

    public T2 f2;

    public Tuple3() {}

    public Tuple3(T0 f0, T1 f1, T2 f2) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public <T> T getField(int pos) {
        switch (pos) {
            case 0:
                return (T) this.f0;
            case 1:
                return (T) this.f1;
            case 2:
                return (T) this.f2;
            default:
                throw new IndexOutOfBoundsException(String.valueOf(pos));
        }
    }

    @Override
    public <T> void setField(T value, int pos) {
        switch (pos) {
            case 0:
                this.f0 = (T0) value;
                break;
            case 1:
                this.f1 = (T1) value;
                break;
            case 2:
                this.f2 = (T2) value;
            default:
                throw new IndexOutOfBoundsException(String.valueOf(pos));
        }
    }
}
