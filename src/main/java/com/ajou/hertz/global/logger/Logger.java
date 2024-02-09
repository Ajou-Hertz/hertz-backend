package com.ajou.hertz.global.logger;

import com.ajou.hertz.global.logger.util.LogTraceUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Log trace id와 함께 로그를 남길 때 사용하기 위한 logger class.
 * <p>Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
 */
@Slf4j
public class Logger {

    /**
     * <p>전달받은 내용을 log trace id와 함께 trace level로 로깅한다.
     * <p>Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    public static void trace(String content) {
        log.trace("[{}] {}", LogTraceUtils.getLogTraceId(), content);
    }

    /**
     * <p>전달받은 내용을 log trace id와 함께 debug level로 로깅한다.
     * <p>Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    public static void debug(String content) {
        log.debug("[{}] {}", LogTraceUtils.getLogTraceId(), content);
    }

    /**
     * <p>전달받은 내용을 log trace id와 함께 info level로 로깅한다.
     * <p>Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    public static void info(String content) {
        log.info("[{}] {}", LogTraceUtils.getLogTraceId(), content);
    }

    /**
     * <p>전달받은 내용을 log trace id와 함께 warn level로 로깅한다.
     * <p>Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    public static void warn(String content) {
        log.warn("[{}] {}", LogTraceUtils.getLogTraceId(), content);
    }

    /**
     * <p>전달받은 내용을 log trace id와 함께 error level로 로깅한다.
     * <p>Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    public static void error(String content) {
        log.error("[{}] {}", LogTraceUtils.getLogTraceId(), content);
    }
}
