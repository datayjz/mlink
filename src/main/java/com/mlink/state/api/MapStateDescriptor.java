package com.mlink.state.api;

import com.mlink.typeinfo.TypeInformation;
import com.mlink.typeinfo.TypeSerializer;
import java.util.Map;

public class MapStateDescriptor<UK, UV>
    extends StateDescriptor<MapState<UK, UV>, Map<UK, UV>> {

    public MapStateDescriptor(String name,
                              Class<UK> keyClass,
                              Class<UV> valueClass) {
        super(name, new TypeInformation<>(), null);
    }

    public MapStateDescriptor(String name,
                              TypeInformation<UK> keyTypeInfo,
                              TypeInformation<UV> valueTypeInfo) {
        super(name, new TypeInformation<>(), null);
    }

    public MapStateDescriptor(String name,
                              TypeSerializer<UK> keySerializer,
                              TypeSerializer<UV> valueSerializer) {
        super(name, new TypeSerializer<>(), null);
    }




    @Override
    public Type getType() {
        return Type.MAP;
    }
}
