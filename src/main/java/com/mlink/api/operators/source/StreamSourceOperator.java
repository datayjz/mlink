package com.mlink.api.operators.source;

import com.mlink.api.functions.source.SourceFunction;
import com.mlink.api.operators.AbstractUdfStreamOperator;

/**
 * Source operator，用于执行SourceFunction。
 * Flink原类名为StreamSource。
 */
public class StreamSourceOperator<OUT, SRC extends SourceFunction<OUT>>
    extends AbstractUdfStreamOperator<OUT, SRC> {

    private transient SourceFunction.SourceContext<OUT> context;

    private transient volatile boolean canceledOrStopped = false;

    public StreamSourceOperator(SRC sourceFunction) {
        super(sourceFunction);

    }

    public void run() {

    }

    @Override
    public void close() throws Exception {

    }

    //先标记，然后停函数、在关闭context
    public void cancel() {
        markCanceledOrStopped();
        userFunction.cancel();;

        if (context != null) {
            context.close();;
        }
    }

    protected void markCanceledOrStopped() {
        this.canceledOrStopped = true;
    }

    protected boolean isCanceledOrStopped() {
        return canceledOrStopped;
    }
}
