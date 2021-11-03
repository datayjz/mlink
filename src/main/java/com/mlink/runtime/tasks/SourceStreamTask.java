package com.mlink.runtime.tasks;

import com.mlink.api.functions.SourceFunction;
import com.mlink.api.operators.StreamSource;

public class SourceStreamTask<OUT, SRC extends SourceFunction<OUT>, OP extends StreamSource<OUT, SRC>>
    extends StreamTask<OUT, OP>{

}
