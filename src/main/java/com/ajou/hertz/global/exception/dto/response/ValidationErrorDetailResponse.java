package com.ajou.hertz.global.exception.dto.response;

public record ValidationErrorDetailResponse(
        String field,
        String message
) {
}
