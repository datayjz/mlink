package com.mlink.api.eventtime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Watermark实体类，主要维护了接收的时间戳
 */
public final class Watermark implements Serializable {

    //使用Thread每个线程维护自己的date formate
    private static final ThreadLocal<SimpleDateFormat> TIMESTAMP_FORMATTER =
        ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));

    public static final Watermark MAX_WATERMARK = new Watermark(Long.MAX_VALUE);

    private final long timestamp;

    public Watermark(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimeStamp() {
        return TIMESTAMP_FORMATTER.get().format(new Date(timestamp));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Watermark watermark = (Watermark) o;
        return timestamp == watermark.timestamp;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(timestamp);
    }
}
