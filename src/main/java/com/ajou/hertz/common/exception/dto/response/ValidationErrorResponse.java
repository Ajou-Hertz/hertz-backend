package com.ajou.hertz.common.exception.dto.response;

import java.util.List;

public record ValidationErrorResponse(
        Integer code,
        String message,
        List<ValidationErrorDetailResponse> errors
) {
}
