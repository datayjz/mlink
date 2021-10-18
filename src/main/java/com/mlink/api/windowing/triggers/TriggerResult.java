package com.mlink.api.windowing.triggers;

/**
 * Trigger中方法返回结果，该结果定义了比如窗口内数据是否要进行计算，是否要丢弃窗口数据。
 */
public enum TriggerResult {

    /**
     * 不对窗口执行任何操作，也就是继续处理数据流
     */
    CONTINUE(false, false),

    /**
     * 触发窗口计算(将窗口数据通过窗口函数计算)，并向下游发送结果数据。
     */
    FIRE_AND_PURGE(true, true),

    /**
     * 进行窗口计算，并向下游发送结果，但不会清空窗口内数据，也就是会继续接收窗口数据。
     */
    FIRE(true, false),

    /**
     * 窗口中所有数据被清除，并丢弃窗口。不会进行窗口计算，和向下游发送结果。
     */
    PURGE(false, true);

    private final boolean fire;
    private final boolean purge;
    TriggerResult(boolean fire, boolean purge) {
        this.fire = fire;
        this.purge = purge;
    }

    public boolean isFire() {
        return fire;
    }

    public boolean isPurge() {
        return purge;
    }
}
