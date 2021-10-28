package com.mlink.api.functions;

/**
 * 用户自定义元素分区器
 */
public interface Partitioner<K> extends Function{

    int partitioner(K key, int numPartitioner);
}
