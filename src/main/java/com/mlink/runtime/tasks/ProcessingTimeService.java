package com.mlink.runtime.tasks;

/**
 * 用于定义当前处理时间，并且处理所有相关的操作，例如注册一个计时器为当前task在之后执行
 */
public interface ProcessingTimeService {

    long getCurrentProcessingTime();


}
