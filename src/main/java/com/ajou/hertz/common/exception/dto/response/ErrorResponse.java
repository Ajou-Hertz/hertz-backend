package com.ajou.hertz.common.exception.dto.response;

public record ErrorResponse(
        Integer code,
        String message
) {
}
