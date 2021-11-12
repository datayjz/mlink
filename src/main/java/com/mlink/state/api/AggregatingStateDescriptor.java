package com.mlink.state.api;

import com.mlink.api.functions.AggregateFunction;
import com.mlink.typeinfo.TypeInformation;
import com.mlink.typeinfo.TypeSerializer;

public class AggregatingStateDescriptor<IN, ACC, OUT>
    extends StateDescriptor <AggregatingState<IN, OUT>, ACC>{

    private final AggregateFunction<IN, ACC, OUT> aggFunction;

    public AggregatingStateDescriptor(String name,
                                      AggregateFunction<IN, ACC, OUT> aggFunction,
                                      Class<ACC> stateType) {
        super(name, stateType, null);
        this.aggFunction = aggFunction;
    }

    public AggregatingStateDescriptor(String name,
                                      AggregateFunction<IN, ACC, OUT> aggFunction,
                                      TypeInformation<ACC> stateType) {
        super(name, stateType, null);
        this.aggFunction = aggFunction;
    }

    public AggregatingStateDescriptor(String name,
                                      AggregateFunction<IN, ACC, OUT> aggFunction,
                                      TypeSerializer<ACC> typeSerializer) {
        super(name, typeSerializer, null);
        this.aggFunction = aggFunction;
    }

    public AggregateFunction<IN, ACC, OUT> getAggFunction() {
        return aggFunction;
    }

    @Override
    public Type getType() {
        return Type.AGGREGATING;
    }
}
