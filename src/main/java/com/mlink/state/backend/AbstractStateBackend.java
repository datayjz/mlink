package com.mlink.state.backend;

import com.mlink.state.AbstractKeyedStateBackend;
import com.mlink.state.KeyGroupRange;
import com.mlink.state.OperatorStateBackend;
import com.mlink.state.backend.StateBackend;
import com.mlink.typeinfo.TypeSerializer;
import java.io.IOException;

/**
 * StateBackend的抽象基类实现，该类目的是为了保证不破坏之前层次结构
 *
 * 需要知道的是StateBackend及其子类，起到的作用是创建存储后端的factory
 */
public abstract class AbstractStateBackend implements StateBackend {


    @Override
    public abstract <K> AbstractKeyedStateBackend createKeyedStateBackend(String operatorIdentifier,
                                                                          TypeSerializer<K> keySerializer,
                                                                          int numberOfKeyGroups,
                                                                          KeyGroupRange keyGroupRange) throws IOException;

    @Override
    public abstract OperatorStateBackend createOperatorStateBackend(String operatorIdentifier) throws Exception;
}
