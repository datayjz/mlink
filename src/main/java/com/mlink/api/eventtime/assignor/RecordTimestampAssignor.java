package com.mlink.api.eventtime.assignor;

import com.mlink.api.eventtime.assignor.TimestampAssigner;

public final class RecordTimestampAssignor<T> implements TimestampAssigner<T> {

    @Override
    public long extractTimestamp(T element, long recordTimestamp) {
        return recordTimestamp;
    }
}
