package com.mlink.state.api;

import java.util.List;

/**
 * list state，即是partition 的keyed state，也可以是operator state。
 *
 * 通常使用ListState相比使用ValueState维护中维护一个List更加高效，因为ListState能够更加高效的append，而不是替换整个原list
 */
public interface ListState<T> extends MergingState<T, Iterable<T>> {

    void update(List<T> values) throws Exception;

    void addAll(List<T> values) throws Exception;
}
