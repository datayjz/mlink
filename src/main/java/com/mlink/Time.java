package com.mlink;

import java.util.concurrent.TimeUnit;

/**
 * 用于Flink中时间相关定义，包括时间单位，时间值
 */
public class Time {

    private final long size;

    private final TimeUnit unit;


    private Time(long size, TimeUnit unit) {
        this.size = size;
        this.unit = unit;
    }

    public long getSize() {
        return size;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public long toMillisecond() {
        return unit.toMillis(size);
    }

    public static Time of(long size, TimeUnit unit) {
        return new Time(size, unit);
    }

    public static Time milliseconds(long milliseconds) {
        return of(milliseconds, TimeUnit.MILLISECONDS);
    }

    public static Time seconds(long seconds) {
        return of(seconds, TimeUnit.SECONDS);
    }

    public static Time minutes(long minutes) {
        return of(minutes, TimeUnit.MINUTES);
    }

    public static Time hours(long hours) {
        return of(hours, TimeUnit.HOURS);
    }

    public static Time days(long days) {
        return of(days, TimeUnit.DAYS);
    }
}
