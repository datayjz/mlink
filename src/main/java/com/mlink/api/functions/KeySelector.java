package com.mlink.api.functions;

/**
 * 用户自定义指定key进行shuffle，该类用于从输入记录中抽取key进行shuffle
 */
public interface KeySelector<IN, KEY> extends Function {

    KEY getKey(IN value);
}
