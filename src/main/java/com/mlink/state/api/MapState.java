package com.mlink.state.api;

import java.util.Iterator;
import java.util.Map;

/**
 * 分区状态(partition state)，状态类型为key-value对
 * UK、UV对应了map value中用户自定的key和value类型
 */
public interface MapState<UK, UV> extends State {


    UV get(UK key) throws Exception;

    void put(UK key, UV value) throws Exception;

    void putAll(Map<UK, UV> map) throws Exception;

    void remove(UK key) throws Exception;

    boolean contains(UK key) throws Exception;

    Iterable<Map.Entry<UK, UV>> entries() throws Exception;

    Iterable<UK> keys() throws Exception;

    Iterable<UV> values() throws Exception;

    Iterator<Map.Entry<UK, UV>> iterator() throws Exception;

    boolean isEmpty() throws Exception;
}
