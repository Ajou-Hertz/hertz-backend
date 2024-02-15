package com.ajou.hertz.common.logger.aop;

import com.ajou.hertz.common.logger.util.LogTraceUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return LogTraceUtils.getLogTraceId();
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPrevId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }
}
