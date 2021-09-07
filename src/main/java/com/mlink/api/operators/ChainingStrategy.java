package com.mlink.api.operators;

/**
 * 用于为Operator指定chaining的策略。如果一个Operator链接到前一个Operator上，意味着它们会运行在一个线程上，该算子变为了多步骤组成的算子。
 */
public enum ChainingStrategy {

    /**
     * 只要允许，就将该Operator链接起来。
     */
    ALWAYS,

    /**
     * 当前Operator不允许链接到前一个或后一个Operator上，也就是该Operator不能chain。
     */
    NEVER,

    /**
     * 当前Operator不链接到前一个Operator上，但是允许后续的Operator链接到当前Operator上。
     *
     * 意思就是当前Operator必须作为chain链中的头节点。
     */
    HEAD,

    /**
     * 和HEAD类似，但是如果能chain到上游source的话，会尽量chain上去。
     */
    HEAD_WITH_SOURCE;

    /**
     * 默认采用ALWAYS策略，因为这样能最大化优化执行性能
     */
    public static final ChainingStrategy DEFAULT_CHAINING_STRATEGY = ALWAYS;
}
