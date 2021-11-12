package com.mlink.state.backend;

import com.mlink.configuration.ReadableConfig;
import com.mlink.state.AbstractKeyedStateBackend;
import com.mlink.state.KeyGroupRange;
import com.mlink.state.OperatorStateBackend;
import com.mlink.typeinfo.TypeSerializer;
import java.io.IOException;

public class HashMapStateBackend extends AbstractStateBackend implements ConfigurableStateBackend {

    public HashMapStateBackend() {

    }

    private HashMapStateBackend(HashMapStateBackend original, ReadableConfig config) {

    }

    @Override
    public <K> AbstractKeyedStateBackend createKeyedStateBackend(String operatorIdentifier,
                                                                 TypeSerializer<K> keySerializer,
                                                                 int numberOfKeyGroups,
                                                                 KeyGroupRange keyGroupRange)
        throws IOException {
        return null;
    }

    @Override
    public OperatorStateBackend createOperatorStateBackend(String operatorIdentifier)
        throws Exception {
        return null;
    }

    @Override
    public StateBackend configure() throws IllegalStateException {
        return null;
    }
}
