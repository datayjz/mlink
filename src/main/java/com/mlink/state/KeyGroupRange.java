package com.mlink.state;

import java.io.Serializable;

/**
 * 定义了key-group索引的范围，key-group是针对job对keyed state处理而划分的key空间。
 */
public class KeyGroupRange implements Serializable {

    private final int startKeyGroup;
    private final int endKeyGroup;

    private KeyGroupRange() {
        this.startKeyGroup = 0;
        this.endKeyGroup = 1;
    }

    public KeyGroupRange(int startKeyGroup, int endKeyGroup) {
        this.startKeyGroup = startKeyGroup;
        this.endKeyGroup = endKeyGroup;
    }

    public static KeyGroupRange of(int startKeyGroup, int endKeyGroup) {
        return new KeyGroupRange(startKeyGroup, endKeyGroup);
    }

    /**
     * 获取key-group范围内个数
     * @return
     */
    public int getNumberOfKeyGroup() {
        return 1 + endKeyGroup - startKeyGroup;
    }

    public int getStartKeyGroup() {
        return startKeyGroup;
    }

    public int getEndKeyGroup() {
        return endKeyGroup;
    }


}
