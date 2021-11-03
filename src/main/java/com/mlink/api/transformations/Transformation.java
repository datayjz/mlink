package com.mlink.api.transformations;

import java.util.List;

/**
 * 创建每个DataStream时都会同时创建一个Transformation，该类用于生成StreamGraph
 */
public abstract class Transformation<T> {

    //Transformation唯一id
    protected final int id;

    //Transformation名称，用于可视化和log
    protected String name;

    //Transformation并行度
    private int parallelism;

    private int maxParallelism = -1;

    protected long bufferTimeout = -1;

    /**
     * 用于为每个Transformation指定唯一ID
     */
    protected static Integer idCounter = 0;
    public static int getNewNodeId() {
        idCounter++;
        return idCounter;
    }

    public Transformation(String name, int parallelism) {
        this.id = getNewNodeId();
        this.name = name;
        this.parallelism = parallelism;
    }

    /**
     * 返回包含当前Transformation的所有上游Transformation
     * @return
     */
    public abstract List<Transformation<?>> getTransitivePredecessors();

    /**
     * 返回该Transformation的直接上游输入Transformation
     */
    public abstract List<Transformation<?>> getInputs();

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public int getMaxParallelism() {
        return maxParallelism;
    }

    public void setMaxParallelism(int maxParallelism) {
        this.maxParallelism = maxParallelism;
    }

    public void setBufferTimeout(long bufferTimeout) {
        this.bufferTimeout = bufferTimeout;
    }

    public long getBufferTimeout() {
        return bufferTimeout;
    }
}
