package com.mlink.api.environment;

/**
 * checkpoint mode 定义了当发生故障后，系统系统的一致性保证
 */
public enum CheckpointingMode {

    EXACTLY_ONCE,

    AT_LEAST_ONCE
}
