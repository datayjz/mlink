package com.mlink.api.environment;

import com.mlink.connector.NumberSequenceSource;
import com.mlink.connector.Source;
import com.mlink.functions.source.FromIteratorFunction;
import com.mlink.operator.StreamSourceOperator;
import com.mlink.typeinfo.TypeInformation;
import java.util.Arrays;
import java.util.Collection;


import com.mlink.api.datastream.DataStreamSource;
import com.mlink.core.configuration.Configuration;
import com.mlink.functions.source.SourceFunction;

public class StreamExecutionEnvironment {

  public StreamExecutionEnvironment() {
    this(new Configuration());
  }

  public StreamExecutionEnvironment(final Configuration configuration) {

  }

  //添加数据源，最终都是通过addSource()和fromSource()来指定的数据源。
  //addSource()是指定自定义的SourceFunction。
  //fromSource()是通过指定source connector

  /**
   * 创建包含指定序列数字的data stream.
   */
  public DataStreamSource<Long> fromSequence(long from, long to) {
    if (from > to) {
      throw new IllegalArgumentException();
    }

    return fromSource(new NumberSequenceSource<>(from, to), "Sequence Source", null);
  }

  public final <OUT> DataStreamSource<OUT> fromElements(OUT... data) {
    if (data.length == 0) {
      throw new IllegalArgumentException();
    }

    return fromCollection(Arrays.asList(data));
  }

  //本质就是通过创建一个Iterator的source function
  public <OUT> DataStreamSource<OUT> fromCollection(Collection<OUT> collections) {
    if (collections.isEmpty()) {
      throw new IllegalArgumentException();
    }
    //TODO 通过类型系统抽取数据类型，该方法应该有重载，可以指定TypeInformation
    TypeInformation<OUT> typeInformation = null;

    SourceFunction<OUT> sourceFunction = new FromIteratorFunction<>(collections.iterator());
    return addSource(sourceFunction, "Collection source", typeInformation);
  }

  public <OUT> DataStreamSource<OUT> addSource(SourceFunction<OUT> sourceFunction,
                                               String sourceName,
                                               TypeInformation<OUT> typeInformation) {

    //把Function给Operator来执行
    StreamSourceOperator<?> sourceOperator = new StreamSourceOperator<>(sourceFunction);
    return new DataStreamSource<>(this, typeInformation, sourceOperator, sourceName);
  }

  public <OUT> DataStreamSource<OUT> fromSource(Source<OUT> source,
                                                String sourceName,
                                                TypeInformation<OUT> typeInfo) {

    return new DataStreamSource<OUT>(this, source, typeInfo, sourceName);
  }
}
