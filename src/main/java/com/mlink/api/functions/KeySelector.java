package com.mlink.api.functions;

/**
 * 对应key by操作，用户通过该函数指定对应进行reduce、group等操作。
 * 该函数接收一个对象，从中抽取对象对应的主键，然后返回，主键后续会用来分区。
 */
public interface KeySelector<IN, KEY> extends Function {

    /**
     * 返回对象主键
     * @param value
     * @return
     */
    KEY getKey(IN value);
}
