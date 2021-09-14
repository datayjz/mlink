package com.mlink.api.eventtime.generator;

import com.mlink.api.eventtime.generator.WatermarkGenerator;

public interface WatermarkGeneratorSupplier<T> {

    WatermarkGenerator<T> createWatermarkGenerator(Context context);

    interface Context {

    }
}
