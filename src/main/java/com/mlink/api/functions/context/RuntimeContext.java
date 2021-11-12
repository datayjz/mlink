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
 * RuntimeContext是Function执行的上下文信息，每个Function的并行实例都有一个RuntimeContext。也就是RuntimeContext是Function
 * 级别的吗，而不是job级别的。RuntimeContext包含了一些静态上下文信息、state等。
 */
public interface RuntimeContext {

    /**
     * 算子任务并行度
     */
    int getNumberOfParallelSubtasks();

    /**
     * 该DAG job中最大算子的并行度
     */
    int getMaxNumberOfParallelSubtasks();

    /**
     * 当前子任务所在算子的并发度的索引ID，0~NumberOfParallelSubtasks-1
     */
    int getIndexOfThisSubtask();

    //----------------accessing state --------------------------//

    // Keyed state

    <T> ValueState<T> getState(ValueStateDescriptor<T> stateDescriptor);

    <T> ListState<T> getListState(ListStateDescriptor<T> stateDescriptor);

    <T>ReducingState<T> getReducingState(ReducingState<T> stateDescriptor);

    <IN, ACC, OUT> AggregatingState<IN, OUT> getAggregatingState(
        AggregatingStateDescriptor<IN, ACC, OUT> stateDescriptor);

    <UK, UV> MapState<UK, UV> getMapState(MapStateDescriptor<UK, UV> stateDescriptor);
}
