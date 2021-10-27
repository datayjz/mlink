package com.mlink.runtime.partitioner;

/**
 * ChannelSelector用于确定record应该写到哪个logical channel里面去
 */
public interface ChannelSelector<T> {

    /**
     * 初始化channel selector，指定输出通道总数
     */
    void setUp(int numberOfChannels);

    /**
     * 返回记录应该输出到channel的索引。注意对于broadcast channel不能调用该方法。
     */
    int selectChannel(T record);

    /**
     *  代表选择所有output channel
     */
    boolean isBroadcast();
}
