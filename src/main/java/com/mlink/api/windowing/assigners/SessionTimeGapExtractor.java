package com.mlink.api.windowing.assigners;

import java.io.Serializable;

public interface SessionTimeGapExtractor<T> extends Serializable {

    long extract(T element);
}
