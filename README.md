# Mlink
Mlink即Mini Flink，项目初衷是在阅读flink源码过程中，将flink核心特性从复杂的工程项目抽出来，以便更容易理解和学习flink。

比如Operator的源码分布在：flink-core、flink-java、flink-streaming-java等模块中，虽然这样存有它的规则，但是对于源码阅读是非常不方便的。

# DatStream API
DataStream API是Flink编写streaming任务的核心API，同时也是SQL和Table API的底层核心支撑。
![DataStream API](doc/datastrea_api.png)

一个Flink DataStream程序主要包括以下五部分：
1. 获取执行环境(StreamExecutionEnvironment)。
2. 添加数据源(Add source)。
3. 对数据集进行转换操作(Transformation)。
4. 指定数据输出(Add sink)。
5. 触发程序执行(Execute)。

而DataStream API的源码也是从这五方面出发。

在看DataStream实现过程中，需要有几个概念需要明白：DataStream、Transformation、StreamOperator、Function和StreamTask。
* DataStream，StreamOperator的抽象，用户可以通过DataStream API来操作Operator。
* StreamOperator，算子的具体实现，但是算子操作的内容，是通过Function指定的。
* Function，算子的具体执行逻辑。
* StreamTask，StreamTask用于执行StreamOperator。
* Transformation，用于将Operator转换为JobGraph。
  用一个不恰当的线性表示，可以理解为：
  Function -> DataStream -> Operator -> StreamTask
  算子计算逻辑传递给DataStream，DataStream将其执行逻辑给到对应的Operator，最后Operator被StreamTask执行。

## Function
com.mlink.api.functions定义了Flink中基础Function，主要包括以下Function：
 * Source(com.mlink.api.functions.source)
    * SourceFunction: Source函数接口，自定义source需要实现该接口。
    * FromIteratorFunction：迭代器的source简单实现。
    * SocketTextStreamFunction：socket读取数据简单实现。
 * Transformation(com.mlink.api.functions.transformation)
    * FilterFunction：filter函数接口。
    * MapFunction：map 函数接口。
    * FlatMapFunction：flat map函数接口。
    * ReduceFunction：reduce 函数接口。
 * Sink(com.mlink.api.functions.sink)
    * SinkFunction：Sink函数接口，自定义Sink可以实现该接口。
    * SinkRichFunction：继承了RichFunction，并且实现了SinkFunction接口的抽象类，为Sink函数提供了RichFunction功能。
    * PrintSinkFunction：元素输出sink简单实现。
上面所有函数都实现自Function或RichFunction接口，Function是所有UDF(user-defined functions)的顶级接口，
而RichFunction是对UDF提供了一些丰富功能的Function接口，比如定义了Function内方法的生命周期，可以访问RuntimeContext等。

在com.mlink.api.functions之下除了定义Function外，还定义了Function所用到的RuntimeContext。RuntimeContext包含了Function
执行过程中的所需的上下文信息，比如算子并行度、最大并行度、state等。另外每个Function实例(并发度下的subtask)都有对应的RuntimeContext。
RuntimeContext定义如下(com.mlink.api.functions.context)：
 * RuntimeContext：Context接口定义。
 * StreamingRuntimeContext：为streaming operator实现的RuntimeContext。

## Operator
com.mlink.api.operators定义了Flink中基础的Operator，这里的Operator类型基本和Function一一对应。主要包括以下Operator：
 * Source(com.mlink.api.operators.source)
    * StreamSourceOperator，source operator，用于执行SourceFunction。
 * Transformation(com.mlink.api.operators.transformation)
    * StreamFilterOperator, filter operator，用于执行FilterFunction。
    * StreamMapOperator，map operator，用于执行MapFunction。
    * StreamFlatMapOperator，flatmap operator，用于执行FlatMapFunction。
    * StreamReduceOperator，reduce operator，用于执行ReduceFunction。
 * Sink(com.mlink.api.operators.sink)
    * StreamSinkOperator, sink operator，用于执行SinkFunction。

上面operator所继承的接口或抽象类定义如下：
 * StreamOperator，operator定义顶层接口，定义了operator声明周期。
 * OneInputStreamOperator，实现该接口的算子只有一个输入流。
 * TwoInputStreamOperator，实现该接口的算子有两个输入流。
 * AbstractStreamOperator，所有operator的抽象基类，所有operator都需要实现该抽象方法。如果对应的operator执行的是UDF
则需要实现其子类AbstractUdfStreamOperator。该抽象类对operator生命周期内容进行了默认实现。
 * AbstractUdfStreamOperator，对于执行用户自定义函数(udf)的Operator，需要实现该抽象类。该类将udf的生命周期作为Operator的生命周期一部分。 

对应关系如下，StreamMap(map operator)和CoProcessOperator都是执行udf的operator，分别对应单流和双流输入。而join 
operator抽象基类不是运行udf的operator，所以直接实现了AbstractStreamOperator。
![operator对应关系](doc/StreamOperator.png)

## 执行环境
StreamExecutionEnvironment从功能上主要分为两大类：
* 提供了控制程序执行的方法，比如设置并发度、checkpoint设置、执行方式等。
* 与外界进行交互。

而对于DataStream API来说，StreamExecutionEnvironment的主要作用就是创建数据源。
