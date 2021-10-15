package com.mlink.api.windowing.time;

import java.util.concurrent.TimeUnit;

/**
 * 用于定义窗口间隔时间的
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