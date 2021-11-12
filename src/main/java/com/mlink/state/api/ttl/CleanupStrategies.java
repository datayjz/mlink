package com.mlink.state.api.ttl;

import java.util.EnumMap;

/**
 * ttl过期数据清除策略。默认情况下，总是在read state发现数据过期时删除过期数据。
 */
public class CleanupStrategies {

    static final CleanupStrategy EMPTY_STRATEGY = new EmptyCleanupStrategy();

    private final boolean isCleanupInBackground;
    private final EnumMap<Strategies, CleanupStrategy> strategies;

    public CleanupStrategies (EnumMap<Strategies, CleanupStrategy> strategies,
                              boolean isCleanupInBackground) {
        this.strategies = strategies;
        this.isCleanupInBackground = isCleanupInBackground;
    }

    public boolean isFullSnapshot() {
        return strategies.containsKey(Strategies.FULL_STATE_SCAN_SNAPSHOT);
    }

    public boolean isCleanupInBackground() {
        return isCleanupInBackground;
    }

    public IncrementalCleanupStrategy getIncrementalCleanupStrategy() {
        IncrementalCleanupStrategy defaultStrategy =
            isCleanupInBackground ? IncrementalCleanupStrategy.DEFAULT_INCREMENTAL_CLEANUP_STRATEGY : null;

        return (IncrementalCleanupStrategy) strategies.getOrDefault(Strategies.INCREMENTAL_CLEANUP, defaultStrategy);
    }

    public boolean inRocksdbCompatFilter() {
        return getRocksDBCompactFilterCleanupStrategy() != null;
    }

    public RocksDBCompactFilterCleanupStrategy getRocksDBCompactFilterCleanupStrategy() {
        RocksDBCompactFilterCleanupStrategy defaultStrategy =
            isCleanupInBackground ?
            RocksDBCompactFilterCleanupStrategy.DEFAULT_ROCKSDB_COMPACT_FILTER_CLEANUP_STRATEGY : null;
        return (RocksDBCompactFilterCleanupStrategy) strategies.getOrDefault(Strategies.ROCKSDB_COMPACTION_FILTER, defaultStrategy);
    }

    //清除策略
    enum Strategies {
        //全量cp时扫描清除
        FULL_STATE_SCAN_SNAPSHOT,
        //增量部分清除
        INCREMENTAL_CLEANUP,
        //基于rocksdb后台compaction清除
        ROCKSDB_COMPACTION_FILTER
    }
}
