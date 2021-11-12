package com.mlink.configuration;

import java.util.Optional;

/**
 * 对配置对象的读取访问，允许读取ConfigOption中的元数据信息
 */
public interface ReadableConfig {

    <T> T get(ConfigOption<T> option);

    <T>Optional<T> getOptional(ConfigOption<T> option);
}
