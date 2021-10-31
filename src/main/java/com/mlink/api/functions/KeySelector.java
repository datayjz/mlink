package com.mlink.api.functions;

/**
 * 从用户记录中抽取分区key的Function
 */
public interface KeySelector<IN, KEY> extends Function {

    KEY getKey(IN value);
}
