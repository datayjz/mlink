package com.mlink.state;

import com.google.common.hash.Hashing;

/**
 * keyGroup相关计算
 */
public class KeyGroupRangeAssignment {

    /**
     * 将key分配到具体operator的并发索引上
     */
    public static int assignKeyToParallelismOperator(Object key,
                                                     int parallelism,
                                                     int maxParallelism) {
        //1. 首先计算该key所在的KeyGroupIndex
        int keyGroupIndex = assignToKeyGroup(key, maxParallelism);
        //2. 计算operator 索引
        return computeOperatorIndexForKeyGroup(maxParallelism, parallelism, keyGroupIndex);
    }

    /**
     * 将key分配到具体的KeyGroupIndex上
     */
    public static int assignToKeyGroup(Object key, int maxParallelism) {
        //1. 对key第一次求hash，使用java hash code
        int keyHash =key.hashCode();
        return computeKeyGroupForKeyHash(keyHash, maxParallelism);
    }

    /**
     * 对keyHash再求Hash，取模最大并发计算KeyGroupIndex
     */
    public static int computeKeyGroupForKeyHash(int keyHash, int maxParallelism) {
        //对keyHash结果在进行murmurHash，目的尽量打散key
        return Hashing.murmur3_32().hashInt(keyHash).asInt() % maxParallelism;
    }

    /**
     * 计算keyGroupIndex对应的operator 索引id
     */
    public static int computeOperatorIndexForKeyGroup(int maxParallelism,
                                                      int parallelism,
                                                      int keyGroupId) {
        return keyGroupId * parallelism / maxParallelism;
    }

}
