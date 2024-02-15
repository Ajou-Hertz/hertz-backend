package com.ajou.hertz.common.exception.dto.response;

public record ValidationErrorDetailResponse(
        String field,
        String message
) {
}
