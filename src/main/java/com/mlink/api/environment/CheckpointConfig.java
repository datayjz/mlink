package com.mlink.api.environment;

import java.io.Serializable;

/**
 * checkpoint相关配合着
 */
public class CheckpointConfig implements Serializable {

    //静态变量

    //默认checkpoint模式 exactly-once
    public static final CheckpointingMode DEFAULT_MODE = CheckpointingMode.EXACTLY_ONCE;
    //默认checkpoint超时时间10min
    public static final long DEFAULT_TIMEOUT = 10 * 60 * 1000;
    //默认两次checkpoint最小间隔，不开启
    public static final long DEFAULT_MIN_PAUSE_BETWEEN_CHECKPOINTS = 0;
    //默认同时执行的checkpoint个数
    public static final int DEFAULT_MAX_CONCURRENT_CHECKPOINTS = 1;
    //容忍checkpoint连续失败个数
    public static final int DEFAULT_TOLERABLE_CHECKPOINT_NUMBER = -1;

    //相关配置
    //checkpoint执行模式(exactly-once or at-least-once)
    private CheckpointingMode checkpointingMode = DEFAULT_MODE;

    //周期触发checkpoint的间隔，-1是关闭
    private long checkpointInterval = -1;

    //checkpoint超时时间，超过该事件则会放弃该次检查点
    private long checkpointTimeout = DEFAULT_TIMEOUT;

    //两次检查点间隔时间
    private long minPauseBetweenCheckpoints = DEFAULT_MIN_PAUSE_BETWEEN_CHECKPOINTS;

    //同一时刻并行执行checkpoint的个数
    private int maxConcurrentCheckpoints = DEFAULT_MAX_CONCURRENT_CHECKPOINTS;

    //是否开启非对齐checkpoint
    private boolean unalignedCheckpointsEnabled;

    //停止job后外部checkpoint数据是否清除
    private ExternalizedCheckpointCleanup externalizedCheckpointCleanup;

    //容忍checkpoint失败个数
    private int tolerableCheckpointFailureNumber = DEFAULT_TOLERABLE_CHECKPOINT_NUMBER;

    private transient Checkpoint

    public CheckpointConfig() {}

    public CheckpointConfig(CheckpointConfig checkpointConfig) {

    }

    /**
     * job停止时外部checkpoint数据处理行为
     */
    public enum ExternalizedCheckpointCleanup {
        /**
         * 在job停止后删除checkpoint数据，意味着job取消后，不能从外部checkpoint数据护肤
         */
        DELETE_ON_CANCELLATION(true),

        /**
         * job取消后，保留外部checkpoint数据
         */
        RETAIN_ON_CANCELLATION(false);

        private final boolean deleteOnCancellation;

        ExternalizedCheckpointCleanup(boolean deleteOnCancellation) {
            this.deleteOnCancellation = deleteOnCancellation;
        }

        public boolean deleteOnCancellation() {
            return deleteOnCancellation;
        }
    }
}
