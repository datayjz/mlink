package com.mlink.api.eventtime.assignor;

import java.io.Serializable;

public interface TimestampAssignerSupplier<T> extends Serializable {

    TimestampAssigner<T> createTimestampAssigner(Context context);

    interface Context {

    }
}
