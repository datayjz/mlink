package com.mlink.api.windowing.assigners;

import com.mlink.api.windowing.windows.TimeWindow;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 用于设置窗口与窗口之间的间隔，也就是当前窗口完成后，下一窗口生成策略
 */
public enum WindowStagger {

    //对齐，也就是窗口和窗口之间没有gap
    ALIGNED {
        @Override
        public long getStaggerOffset(long currentProcessingTime, long size) {
            return 0;
        }
    },

    //窗口间随机一个时间段gap(随机数在0 ~ size间)
    RANDOM {
        @Override
        public long getStaggerOffset(long currentProcessingTime, long size) {
            return (long) ThreadLocalRandom.current().nextDouble() * size;
        }
    },

    //以第一个元素到达时间作为窗口开始时间偏差，比如window是1h，第一个元素是00:15:00到达，那么1h窗口的计算之后就是00:15:00 ~ 01:14:59这种
    NATURAL {
        @Override
        public long getStaggerOffset(long currentProcessingTime, long size) {
            long currentProcessWindowStart =
                TimeWindow.getWindowStartWithOffset(currentProcessingTime, 0 , size);
            return Math.max(0, currentProcessingTime - currentProcessWindowStart);
        }
    };

    public abstract long getStaggerOffset(final long currentProcessingTime, final long size);
}
