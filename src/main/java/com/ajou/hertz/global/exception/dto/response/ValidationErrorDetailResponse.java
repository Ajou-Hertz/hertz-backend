package com.ajou.hertz.global.exception.dto.response;

public record ValidationErrorDetailResponse(
        Integer code,
        String field,
        String message
) {
}
