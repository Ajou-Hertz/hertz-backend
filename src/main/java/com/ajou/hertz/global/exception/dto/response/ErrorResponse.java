package com.ajou.hertz.global.exception.dto.response;

public record ErrorResponse(
        Integer code,
        String message
) {
}
