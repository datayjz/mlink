package com.mlink.api.operators;

import com.mlink.api.eventtime.TimeCharacteristic;
import com.mlink.api.functions.SourceFunction;

/**
 * Stream source operator，用于执行SourceFunction。
 * 实际就是StreamTask拉起StreamSource(Operator)
 * 的run方法，StreamSource在调SourceFunction的run方法，SourceFunction通过while(true)一直从外部读取数据发送
 */
public class StreamSource<OUT, SRC extends SourceFunction<OUT>>
    extends AbstractUdfStreamOperator<OUT, SRC> {

    private transient SourceFunction.SourceContext<OUT> sourceContext;

    public StreamSource(SRC sourceFunction) {
        //给AbstractUDFStreamOperator，udf operator将function生命周期添加到operator内
        super(sourceFunction);
        //合理source没有上游了，所以也没必要和上游chain
        this.chainingStrategy = ChainingStrategy.HEAD;
    }

    public void run() {
        //TODO 配置中获取TimeCharacteristic
        TimeCharacteristic timeCharacteristic = TimeCharacteristic.ProcessingTime;
        this.sourceContext = StreamSourceContext.getSourceContext(timeCharacteristic, output);
        //上面创建的SourceContext传递给Function

        //开始执行source
        userFunction.run(sourceContext);
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (sourceContext != null) {
            sourceContext.close();
        }
    }

    //先标记，然后停函数、在关闭context
    public void cancel() {
        userFunction.cancel();;

        if (sourceContext != null) {
            sourceContext.close();;
        }
    }
}
