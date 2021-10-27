package com.mlink.api.transformations;

/**
 * 算子间数据交换的模式，用于生成的StreamGraph中
 */
public enum StreamExchangeMode {

    /**
     * 生产者和消费者同时在线，生产后的数据会被consumer立即接收
     */
    PIPELINED,

    /**
     * 生产者先完成整体数据的产生，然后启动消费者消费数据。和MapReduce模型一样，map先启动处理数据，生成中间结果，reduce拉起来消费
     */
    BATCH,

    /**
     * 未定义交换模式，由框架决定使用PIPELINED还是BATCH模式
     */
    UNDEFINED
}
