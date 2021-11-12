package com.mlink.api.functions.context;

import com.mlink.state.api.AggregatingState;
import com.mlink.state.api.AggregatingStateDescriptor;
import com.mlink.state.api.ListState;
import com.mlink.state.api.ListStateDescriptor;
import com.mlink.state.api.MapState;
import com.mlink.state.api.MapStateDescriptor;
import com.mlink.state.api.ReducingState;
import com.mlink.state.api.ValueState;
import com.mlink.state.api.ValueStateDescriptor;

/**
 * UDF operator来创建的RuntimeContext
 */
public abstract class AbstractRuntimeUDFContext implements RuntimeContext{

    public AbstractRuntimeUDFContext() {

    }

    @Override
    public <T> ValueState<T> getState(ValueStateDescriptor<T> stateDescriptor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> ListState<T> getListState(ListStateDescriptor<T> stateDescriptor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> ReducingState<T> getReducingState(ReducingState<T> stateDescriptor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <IN, ACC, OUT> AggregatingState<IN, OUT> getAggregatingState(
        AggregatingStateDescriptor<IN, ACC, OUT> stateDescriptor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <UK, UV> MapState<UK, UV> getMapState(MapStateDescriptor<UK, UV> stateDescriptor) {
        throw new UnsupportedOperationException();
    }
}
