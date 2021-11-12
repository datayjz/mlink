package com.mlink.state.backend;

import com.mlink.state.CheckpointableKeyedStateBackend;
import com.mlink.state.KeyGroupRange;
import com.mlink.state.OperatorStateBackend;
import com.mlink.typeinfo.TypeSerializer;
import java.io.Serializable;

/**
 * 状态存储后端，定义了streaming应用中的state存储在集群的位置。不同的存储后端，使用不同的方式进行存储，并且使用不同的数据结构来保存正在运行的任务的state
 *
 * Flink提供了两种状态存储后端：
 * HashMapStateBackend：轻量级状态存储后端，无任何依赖，state存储在TaskManager的内存中。
 * EmbeddedRocksDBStateBackend：使用嵌入式RocksDB存储state，能够扩展state大小到TB级别，仅受TaskManager磁盘大小限制。
 *
 * StateBackend为KeyedState和Operator state提供状态存储能力，该接口定义提供了创建keyed
 * state的CheckpointableKeyedStateBackend和operator state的OperatorStateBackend。它们定义了如何保存state。
 * */
public interface StateBackend extends Serializable {

    <K> CheckpointableKeyedStateBackend createKeyedStateBackend(String operatorIdentifier,
                                                                TypeSerializer<K> keySerializer,
                                                                int numberOfKeyGroups,
                                                                KeyGroupRange keyGroupRange) throws Exception;

    /**
     * 使用给定的托管内存来创建一个新的Backend
     */
    default <K> CheckpointableKeyedStateBackend createKeyedStateBackend(String operatorIdentifier,
                                                                        TypeSerializer<K> keySerializer,
                                                                        int numberOfKeyGroups,
                                                                        KeyGroupRange keyGroupRange,
                                                                        double managedMemoryFraction) throws Exception {
        //默认实现不使用管理内存
        return createKeyedStateBackend(
            operatorIdentifier,
            keySerializer,
            numberOfKeyGroups,
            keyGroupRange);
    }

    /**
     * 创建一个新的OperatorStateBackend
     */
    OperatorStateBackend createOperatorStateBackend(String operatorIdentifier) throws Exception;

    /**
     * StateBackend是否使用管理内存
     */
    default boolean useManagedMemory() {
        return false;
    }

}
