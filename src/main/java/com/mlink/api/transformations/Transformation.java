package com.mlink.api.transformation;

import com.mlink.typeinfo.TypeInformation;
import java.util.List;

/**
 * 创建每个DataStream时都会同时创建一个Transformation，该类用于生成StreamGraph
 */
public abstract class Transformation<OUT> {

    //Transformation唯一id
    protected final int id;

    //Transformation名称，用于可视化和log
    protected String name;

    protected TypeInformation<OUT> outputType;

    //Transformation并行度
    private int parallelism;
    /**
     * 用于为每个Transformation指定唯一ID
     */
    protected static Integer idCounter = 0;
    public static int getNewNodeId() {
        idCounter++;
        return idCounter;
    }

    public Transformation(String name, TypeInformation<OUT> outputType, int parallelism) {
        this.id = getNewNodeId();
        this.name = name;
        this.outputType = outputType;
        this.parallelism = parallelism;
    }

    public abstract List<Transformation<?>> getTransitivePredecessors();
    /**
     * 返回转换图中当前Transformation的直接前任Transformation。
     */
    public abstract List<Transformation<?>> getInputs();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParallelism() {
        return parallelism;
    }

    public TypeInformation<OUT> getOutputType() {
        return outputType;
    }
}
