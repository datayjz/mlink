package com.mlink.state.api.ttl;

/**
 * 对于rocksdb过期数据清除策略，使用自定义compaction filter完成
 */
public class RocksDBCompactFilterCleanupStrategy implements CleanupStrategy{

    static final RocksDBCompactFilterCleanupStrategy DEFAULT_ROCKSDB_COMPACT_FILTER_CLEANUP_STRATEGY =
        new RocksDBCompactFilterCleanupStrategy(1000L);


    //更新当前时间戳前，rocksdb compact filter处理state的实例个数
    private final long queryTimeAfterNumEntries;

    public RocksDBCompactFilterCleanupStrategy(long queryTimeAfterNumEntries) {
        this.queryTimeAfterNumEntries = queryTimeAfterNumEntries;
    }

    public static RocksDBCompactFilterCleanupStrategy getDefaultRocksdbCompactFilterCleanupStrategy() {
        return DEFAULT_ROCKSDB_COMPACT_FILTER_CLEANUP_STRATEGY;
    }
}
