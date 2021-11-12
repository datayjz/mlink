package com.mlink.state.api.ttl;

import com.mlink.Time;
import com.mlink.state.api.ttl.CleanupStrategies.Strategies;
import java.io.Serializable;
import java.util.EnumMap;

/**
 * 配置state ttl相关配置
 */
public class StateTtlConfig implements Serializable {

    public static final StateTtlConfig DISABLED = null;

    private final UpdateType updateType;
    private final StateVisibility stateVisibility;
    private final TtlTimeCharacteristic ttlTimeCharacteristic;
    private final Time ttl;
    private final CleanupStrategies cleanupStrategies;

    private StateTtlConfig(UpdateType updateType,
                           StateVisibility stateVisibility,
                           TtlTimeCharacteristic ttlTimeCharacteristic,
                           Time ttl,
                           CleanupStrategies cleanupStrategies) {
        this.updateType = updateType;
        this.stateVisibility = stateVisibility;
        this.ttlTimeCharacteristic = ttlTimeCharacteristic;
        this.ttl = ttl;
        this.cleanupStrategies = cleanupStrategies;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public StateVisibility getStateVisibility() {
        return stateVisibility;
    }

    public TtlTimeCharacteristic getTtlTimeCharacteristic() {
        return ttlTimeCharacteristic;
    }

    public Time getTtl() {
        return ttl;
    }

    public boolean isEnabled() {
        return updateType != UpdateType.Disabled;
    }

    public static Builder newBuilder(Time ttl) {
        return new Builder(ttl);
    }

    public static class Builder {
        //默认last timestamp为创建和写入时间
        private UpdateType updateType = UpdateType.OnCreateAndWrite;
        //默认过期不返回
        private StateVisibility stateVisibility = StateVisibility.NeverReturnExpired;
        private TtlTimeCharacteristic ttlTimeCharacteristic = TtlTimeCharacteristic.ProcessingTime;
        private Time ttl;
        private boolean isCleanupInBackground = true;
        private final EnumMap<CleanupStrategies.Strategies, CleanupStrategy> strategies
            = new EnumMap<>(CleanupStrategies.Strategies.class);

        public Builder(Time ttl) {
            this.ttl = ttl;
        }

        public Builder setUpdateType(UpdateType updateType) {
            this.updateType = updateType;
            return this;
        }

        public Builder setUpdateTtlOnCreateAndWrite() {
            return setUpdateType(UpdateType.OnCreateAndWrite);
        }

        public Builder setUpdateTtlOnReadAndWrite() {
            return setUpdateType(UpdateType.OnReadAndWrite);
        }

        public Builder setStateVisibility(StateVisibility stateVisibility) {
            this.stateVisibility = stateVisibility;
            return this;
        }

        public Builder returnExpiredIfNotCleanedUp() {
            return setStateVisibility(StateVisibility.ReturnExpiredIfNotCleanUp);
        }

        public Builder neverReturnExpired() {
            return setStateVisibility(StateVisibility.NeverReturnExpired);
        }

        public void setTtlTimeCharacteristic(TtlTimeCharacteristic ttlTimeCharacteristic) {
            this.ttlTimeCharacteristic = ttlTimeCharacteristic;
        }

        /**
         * 生成全量快照时删除过期数据
         */
        public Builder cleanupFullSnapshot() {
            strategies.put(Strategies.FULL_STATE_SCAN_SNAPSHOT, CleanupStrategies.EMPTY_STRATEGY);
            return this;
        }

        public Builder cleanupIncrementally() {
            return this;
        }

        /**
         * 在rocksdb执行compaction过程中，删除过期数据。
         * RocksDB的compact filter会在每处理queryTimeAfterNumEntries
         * 个示例后，查询当前时间戳。频繁更新时间戳可以增加清理效率，但是会降低compact性能。
         */
        public Builder cleanupInRocksdbCompactFilter(long queryTimeAfterNumEntries) {
            strategies.put(Strategies.ROCKSDB_COMPACTION_FILTER,
                new RocksDBCompactFilterCleanupStrategy(queryTimeAfterNumEntries));
            return this;
        }

        public Builder disableCleanupInBackground() {
            this.isCleanupInBackground = false;
            return this;
        }

        public void setTtl(Time ttl) {
            this.ttl = ttl;
        }

        public StateTtlConfig build() {
            return new StateTtlConfig(
                updateType,
                stateVisibility,
                ttlTimeCharacteristic,
                ttl,
                new CleanupStrategies(strategies, isCleanupInBackground));
        }
    }

    /**
     * ttl最后访问时间戳更新方式，比如只有在创建、写入时更新state更新最后操作时间戳，或者每次读取也会更新时间戳
     */
    public enum UpdateType {
        //禁用ttl，状态不会过期
        Disabled,
        //上次访问时间戳只有在每次创建和写入更新时设置
        OnCreateAndWrite,
        //包括OnCreateAndWrite外还包括读操作
        OnReadAndWrite
    }

    /**
     * 用于配置过期数据是否返回
     */
    public enum StateVisibility {
        //过期数据如果还没有清除，则返回
        ReturnExpiredIfNotCleanUp,
        //不返回过期数据(无论数据是否过期)
        NeverReturnExpired
    }

    /**
     * ttl的时间语义，只支持process time
     */
    public enum TtlTimeCharacteristic {
        ProcessingTime
    }

}
