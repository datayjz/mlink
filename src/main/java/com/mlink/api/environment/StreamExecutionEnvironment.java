package com.mlink.api.environment;

import com.mlink.api.Boundedness;
import com.mlink.api.common.CheckpointConfig;
import com.mlink.api.common.ExecutionConfig;
import com.mlink.api.common.JobClient;
import com.mlink.api.common.JobExecutionResult;
import com.mlink.api.common.RuntimeExecutionMode;
import com.mlink.api.functions.source.ParallelSourceFunction;
import com.mlink.api.graph.StreamGraph;
import com.mlink.api.functions.source.FromIteratorFunction;
import com.mlink.api.functions.source.SocketTextStreamFunction;
import com.mlink.api.graph.StreamGraphGenerator;
import com.mlink.api.operators.source.StreamSourceOperator;
import com.mlink.api.transformations.Transformation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


import com.mlink.api.datastream.DataStreamSource;
import com.mlink.core.configuration.Configuration;
import com.mlink.api.functions.source.SourceFunction;
import java.util.List;

/**
 * StreamExecutionEnvironment提供了执行流应用程序的上下文，分为本地执行LocalStreamEnvironment
 * 和远端集群模式RemoteStreamEnvironment。
 * StreamExecutionEnvironment主要包括以下几部分内容：
 *  1. 根据应用程序执行环境创建LocalStreamEnvironment或RemoteStreamEnvironment。二者都是StreamExecutionEnvironment
 *  的子类，LocalStreamEnvironment指定了部署方式为local，会在后台嵌入启动一个flink应用集群。RemoteStreamEnvironment
 *  通过指定远端Flink集群的master(JobManager)来提交应用程序到远端集群。
 *  2. job 基础设置，比如默认并发、最大并发、运行模式、buffer timeout以及operator chaining。
 *  3. Checkpoint 配置(TODO)
 *  4. 指定data source，来创建SourceDataStream。
 *  5. 提交执行。分为同步执行和异步，同步执行实际也是调用的异步执行。首先会生成StreamGraph。
 */
public class StreamExecutionEnvironment {

  private static final ThreadLocal<StreamExecutionEnvironmentFactory> threadLocalContextEnvironmentFactory = new ThreadLocal<>();

  private long bufferTimeout = -1;

  private boolean isChainingEnabled = true;

  private final ExecutionConfig config = new ExecutionConfig();
  private final CheckpointConfig checkpointConfig = null;
  protected final Configuration configuration;

  private final List<Transformation<?>> transformations = new ArrayList<>();

  private final ClassLoader userClassloader;

  //---------------------------- 构造方法 ----------------------------//
  public StreamExecutionEnvironment() {
    this(new Configuration());
  }

  public StreamExecutionEnvironment(final Configuration configuration) {
    this(configuration, null);
  }

  public StreamExecutionEnvironment(final Configuration configuration,
                                    final ClassLoader userClassloader) {
    this.configuration = new Configuration(configuration);
    this.userClassloader = userClassloader == null ? getClass().getClassLoader() : userClassloader;
  }

  //---------------------------- ExecutionEnvironment工厂方法相关 ----------------------------//

  /**
   * 创建一个表示当前程序上下文的执行环境，如果程序是独立调用，则返回本地执行环境
   */
  public static StreamExecutionEnvironment getExecutionEnvironment() {
    return getExecutionEnvironment(new Configuration());
  }

  public static StreamExecutionEnvironment getExecutionEnvironment(Configuration configuration) {
    return threadLocalContextEnvironmentFactory.get().createExecutionEnvironment(configuration);
  }

  /**
   * 创建本地执行环境，以多线程的形式在单个JVM中运行。本地执行环境默认并发度和硬件有关为：CPU cores / threads.
   */
  public static LocalStreamEnvironment createLocalEnvironment() {
    //当前JVM可用处理器数
    return createLocalEnvironment(Runtime.getRuntime().availableProcessors());
  }
  public static LocalStreamEnvironment createLocalEnvironment(int parallelism) {
    return createLocalEnvironment(parallelism, new Configuration());
  }
  public static LocalStreamEnvironment createLocalEnvironment(int parallelism,
                                                              Configuration configuration) {
    //parallelism set to config
    Configuration copyOfConfiguration = new Configuration();
    //copyOfConfiguration.addAll(configuration);
    //copyOfConfiguration.set();
    return new LocalStreamEnvironment(copyOfConfiguration);
  }

  /**
   * 创建RemoteStreamEnvironment，远程环境会将程序发送到cluster执行。
   */
  public static RemoteStreamEnvironment createRemoteEnvironment(String host,
                                                                int port,
                                                                int parallelism,
                                                                String... jarFiles) {
    RemoteStreamEnvironment env = new RemoteStreamEnvironment(host, port, jarFiles);
    env.setParallelism(parallelism);
    return env;
  }


  //---------------------------- job基本参数配置(并发、chaining、buffer timeout、运行模式等)----------------------------//

  /**
   * 为全局所有算子设置一个并发度，该配置会覆盖Environment的默认配置，比如LocalStreamEnvironment默认并发和硬件相关。
   */
  public StreamExecutionEnvironment setParallelism(int parallelism) {
    config.setParallelism(parallelism);
    return this;
  }

  public int getParallelism() {
    return config.getParallelism();
  }

  /**
   * 为job设置最大并发度，该最大并发度是动态扩缩的上线，同时也是用于分区状态key group的数量。
   * 范围为：0 < maxParallelism <= Short.MAX_VALUE (2^15 - 1)
   * 和Ray Streaming一致
   */
  public StreamExecutionEnvironment setMaxParallelism(int maxParallelism) {
    config.setMaxParallelism(maxParallelism);
    return this;
  }

  public int getMaxParallelism() {
    return config.getMaxParallelism();
  }


  /**
   * 设置应用程序执行模式，和execution.runtime-mode配置文件配置等价。
   */
  public StreamExecutionEnvironment setRuntimeMode(final RuntimeExecutionMode executionMode) {
    //configuration.set();
    return this;
  }

  /**
   * 节点通信buffer flush频率间隔，该参数的调整可以来平衡吞吐和延迟。
   * 如果为0，则每条record都会flush，这样延迟最低，但是吞吐会非常低。
   * 如果为-1则只有buffer填充满后才会flush，这时候的吞吐是最大的。
   */
  public StreamExecutionEnvironment setBufferTimeout(long timeoutMills) {
    this.bufferTimeout = timeoutMills;
    return this;
  }

  public long getBufferTimeout() {
    return bufferTimeout;
  }

  /**
   * 关闭operator chaining，operator chaining允许对于非shuffle算子co-located在同一个线程，从而避免数据的序列化和反序列化。
   */
  public StreamExecutionEnvironment disableOperatorChaining() {
    this.isChainingEnabled = false;
    return this;
  }

  public boolean isChainingEnabled() {
    return isChainingEnabled;
  }

  //---------------------------- DataStream source 相关 ----------------------------//

  //添加数据源，最终都是通过addSource()和fromSource()来指定的数据源。
  //addSource()是指定自定义的SourceFunction。
  //fromSource()是通过指定source connector

  public final <OUT> DataStreamSource<OUT> fromElements(OUT... data) {
    return fromCollection(Arrays.asList(data));
  }

  //本质就是通过创建一个Iterator的source function
  public <OUT> DataStreamSource<OUT> fromCollection(Collection<OUT> collections) {
    SourceFunction<OUT> sourceFunction = new FromIteratorFunction<>(collections.iterator());
    return addSource(sourceFunction, "Collection source");
  }


  public DataStreamSource<String> socketTextStream(String hostname, int port) {
    return socketTextStream(hostname, port, "\n", 0);
  }

  public DataStreamSource<String> socketTextStream(String hostname,
                                                   int port,
                                                   String delimiter,
                                                   long maxRetry) {
    return addSource(
        new SocketTextStreamFunction(hostname, port, delimiter, maxRetry), "Socket Stream");
  }

  /**
   * 通过SourceFunction来添加一个数据源到流拓扑，默认SourceFunction执行并发度为1，如果要并行执行则需要实现ParallelSourceFunction
   * 或RichParallelSourceFunction。
   */
  public <OUT> DataStreamSource<OUT> addSource(SourceFunction<OUT> sourceFunction) {
    return addSource(sourceFunction, "Custom source");
  }

  public <OUT> DataStreamSource<OUT> addSource(SourceFunction<OUT> sourceFunction,
                                               String sourceName) {
    return addSource(sourceFunction, sourceName, Boundedness.CONTINUOUS_UNBOUNDED);
  }

  public <OUT> DataStreamSource<OUT> addSource(final SourceFunction<OUT> sourceFunction,
                                               final String sourceName,
                                               final Boundedness boundedness) {

    boolean isParallel = sourceFunction instanceof ParallelSourceFunction;

    //把Function给Operator来执行
    StreamSourceOperator<OUT, ?> sourceOperator = new StreamSourceOperator<>(sourceFunction);
    return new DataStreamSource<>(this, sourceOperator, isParallel, sourceName, boundedness);
  }

  //---------------------------- execute 相关 ----------------------------//

  /**
   * 触发程序执行
   */
  public JobExecutionResult execute() {
    return execute(getStreamGraph());
  }

  public JobExecutionResult execute(String jobName) {
    final StreamGraph streamGraph = getStreamGraph();
    //steamGraph.setJobName(jobName);
    return execute(streamGraph);
  }

  public JobExecutionResult execute(StreamGraph streamGraph) {
    final JobClient jobClient = executeAsync(streamGraph);

    final JobExecutionResult jobExecutionResult = null;

    //TODO 分为attached模式和detached模式
    //jobExecutionResult = jobClient.getJobExecutionResult().get();
    return jobExecutionResult;
  }

  /**
   * 触发程序异步执行
   */
  public final JobClient executeAsync() throws Exception {
    return executeAsync(getStreamGraph());
  }

  public final JobClient executeAsync(String jobName) {
    final StreamGraph streamGraph = getStreamGraph();
    //streamGraph.setJobName(jobName);
    return executeAsync(streamGraph);
  }

  public JobClient executeAsync(StreamGraph streamGraph) {
    //TODO
    return null;
  }

  /**
   * 获取Streaming job的StreamGraph，可选择是否清除之前注册的Transformation。StreamGraph由Transformation构成。
   */
  public StreamGraph getStreamGraph() {
    return getStreamGraph(true);
  }

  public StreamGraph getStreamGraph(boolean clearTransformation) {
    final StreamGraph streamGraph = getStreamGraphGenerator(transformations).generate();
    if (clearTransformation) {
      transformations.clear();
    }
    return streamGraph;
  }

  /**
   * 根据Transformation构建StreamGraph
   */
  public StreamGraph getStreamGraph(List<Transformation<?>> transformations) {
    return getStreamGraphGenerator(transformations).generate();
  }

  /**
   * StreamGraph生成器
   */
  private StreamGraphGenerator getStreamGraphGenerator(List<Transformation<?>> transformations) {
    return new StreamGraphGenerator(transformations);
  }

}
