package com.mlink.api.environment;

import com.mlink.api.common.ExecutionConfig;
import com.mlink.api.datastream.DataStreamSource;
import com.mlink.api.functions.SourceFunction;
import com.mlink.api.graph.streamgraph.StreamGraph;
import com.mlink.api.graph.streamgraph.StreamGraphGenerator;
import com.mlink.api.operators.StreamSource;
import com.mlink.api.transformations.Transformation;
import com.mlink.configuration.Configuration;
import com.mlink.execution.DefaultExecutorServiceLoader;
import com.mlink.execution.PipelineExecutorFactory;
import com.mlink.execution.PipelineExecutorServiceLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * StreamExecutionEnvironment提供了执行流应用程序的上下文，分为本地执行LocalStreamEnvironment
 * 和远端集群模式RemoteStreamEnvironment。
 * StreamExecutionEnvironment主要包括以下几部分内容：
 * 1. 根据应用程序执行环境创建LocalStreamEnvironment或RemoteStreamEnvironment。二者都是StreamExecutionEnvironment
 * 的子类，LocalStreamEnvironment指定了部署方式为local，会在后台嵌入启动一个flink应用集群。RemoteStreamEnvironment
 * 通过指定远端Flink集群的master(JobManager)来提交应用程序到远端集群。
 * 2. job 基础设置，比如默认并发、最大并发、运行模式、buffer timeout以及operator chaining。
 * 3. Checkpoint 配置(TODO)
 * 4. 指定data source，来创建SourceDataStream。
 * 5. 提交执行。分为同步执行和异步，同步执行实际也是调用的异步执行。首先会生成StreamGraph。
 */
public class StreamExecutionEnvironment {

    private static final ThreadLocal<StreamExecutionEnvironmentFactory> threadLocalContextEnvironmentFactory = new ThreadLocal<>();

    private long bufferTimeout = -1;

    private boolean isChainingEnabled = true;

    private final ExecutionConfig config = new ExecutionConfig();

    private final List<Transformation<?>> transformations = new ArrayList<>();

    //执行pipeline的executor
    private final PipelineExecutorServiceLoader executorServiceLoader;


    //---------------------------- 构造方法 ----------------------------//
    public StreamExecutionEnvironment() {
        //默认使用DefaultExecutorServiceLoader
        this.executorServiceLoader = new DefaultExecutorServiceLoader();
    }


    public static StreamExecutionEnvironment getExecutionEnvironment() {
        return getExecutionEnvironment(new Configuration());
    }

    public static StreamExecutionEnvironment getExecutionEnvironment(Configuration configuration) {
        //TODO 先使用LocalExecutionEnvironment
        return StreamExecutionEnvironment.createLocalEnvironment(configuration);
    }

    /**
     * 创建本地执行环境，以多线程的形式在单个JVM中运行。本地执行环境默认并发度和硬件有关为：CPU cores / threads.
     */
    public static LocalStreamEnvironment createLocalEnvironment(Configuration configuration) {
        //当前JVM可用处理器数
        return new LocalStreamEnvironment();
    }

    /**
     * 创建RemoteStreamEnvironment，远程环境会将程序发送到cluster执行。
     */
    public static RemoteStreamEnvironment createRemoteEnvironment(String host, int port,
                                                                  int parallelism,
                                                                  String... jarFiles) {
        RemoteStreamEnvironment env = new RemoteStreamEnvironment(host, port, jarFiles);
        env.setParallelism(parallelism);
        return env;
    }

    //---------------------------- job基本参数配置(并发、chaining、buffer timeout、运行模式等)
  // ----------------------------//

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

    /**
     * 通过SourceFunction来添加一个数据源到流拓扑，默认SourceFunction执行并发度为1，如果要并行执行则需要实现ParallelSourceFunction
     * 或RichParallelSourceFunction。
     */
    public <OUT> DataStreamSource<OUT> addSource(final SourceFunction<OUT> sourceFunction,
                                                 final String sourceName) {

        //把Function给Operator来执行
        StreamSource<OUT, ?> sourceOperator = new StreamSource<>(sourceFunction);
        return new DataStreamSource<>(this, sourceOperator, sourceName);
    }

    //---------------------------- execute 相关 ----------------------------//

    /**
     * 触发程序执行
     */

    public void execute(String jobName) {
        final StreamGraph streamGraph = getStreamGraph();

        //TODO
    }

    public void executeAsync(StreamGraph streamGraph) throws Exception {
      //通过ExecutorService来获取ExecutorFactory
      final PipelineExecutorFactory executorFactory = executorServiceLoader.getExecutorFactory();

    }

    /**
     * 获取Streaming job的StreamGraph，可选择是否清除之前注册的Transformation。StreamGraph由Transformation构成。
     */
    public StreamGraph getStreamGraph() {
        StreamGraphGenerator graphGenerator = new StreamGraphGenerator(transformations);
        StreamGraph streamGraph = graphGenerator.generate();
        transformations.clear();
        return streamGraph;
    }
}
