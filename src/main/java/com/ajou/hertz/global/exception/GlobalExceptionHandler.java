package com.ajou.hertz.global.exception;

import com.ajou.hertz.global.common.exception.CustomException;
import com.ajou.hertz.global.exception.constant.GlobalExceptionType;
import com.ajou.hertz.global.exception.constant.ValidationErrorCode;
import com.ajou.hertz.global.exception.dto.response.ErrorResponse;
import com.ajou.hertz.global.exception.dto.response.ValidationErrorDetailResponse;
import com.ajou.hertz.global.exception.dto.response.ValidationErrorResponse;
import com.ajou.hertz.global.exception.util.ExceptionUtils;
import com.ajou.hertz.global.exception.util.ViolationMessageResolver;
import com.ajou.hertz.global.logger.Logger;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * <p>{@code @ExceptionHandler} method를 통해 Application에서 발생하는 모든 exception들을 처리하는 class.
 *
 * <p>이 class는 Spring MVC exception을 처리하는 {@link ResponseEntityExceptionHandler}을 상속받아 구현되었음.
 * 이 때문에 Spring MVC에서 발생할 수 있는 기본 exception들을 전부 처리하며,
 * 일부 exception의 경우 응답 형태를 재가공하여 client에게 응답한다.
 *
 * @see ResponseEntityExceptionHandler
 * @see org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        Logger.error(String.format(String.format("Custom Exception: %s:", ExceptionUtils.getExceptionStackTrace(ex))));

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Logger.error(String.format("Validation Exception: %s", ExceptionUtils.getExceptionStackTrace(ex)));

        List<ValidationErrorDetailResponse> errorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorDetailResponse(
                        ValidationErrorCode.getErrorCode(fieldError.getCode()),
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                )).toList();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ValidationErrorResponse(
                        GlobalExceptionType.METHOD_ARGUMENT_NOT_VALID.getCode(),
                        GlobalExceptionType.METHOD_ARGUMENT_NOT_VALID.getMessage(),
                        errorDetails
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Logger.error(String.format("Validation Exception: %s", ExceptionUtils.getExceptionStackTrace(ex)));

        List<ValidationErrorDetailResponse> errorDetails = ex.getConstraintViolations().stream()
                .map(violation -> {
                    ViolationMessageResolver resolver = new ViolationMessageResolver(violation);
                    return new ValidationErrorDetailResponse(resolver.getErrorCode(), resolver.getFieldName(), resolver.getMessage());
                }).toList();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ValidationErrorResponse(
                        GlobalExceptionType.CONSTRAINT_VIOLATION.getCode(),
                        GlobalExceptionType.CONSTRAINT_VIOLATION.getMessage(),
                        errorDetails)
                );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        Logger.error(String.format("Spring MVC Basic Exception: %s", ExceptionUtils.getExceptionStackTrace(ex)));

        GlobalExceptionType exceptionType = GlobalExceptionType.from(ex.getClass()).orElse(GlobalExceptionType.UNHANDLED);
        return ResponseEntity
                .status(statusCode)
                .body(new ErrorResponse(
                        exceptionType.getCode(),
                        exceptionType.getMessage() + " " + ex.getMessage())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        Logger.error(String.format("UnHandled Exception: %s", ExceptionUtils.getExceptionStackTrace(ex)));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        GlobalExceptionType.UNHANDLED.getCode(),
                        GlobalExceptionType.UNHANDLED.getMessage() + " " + ex.getMessage()
                ));
    }
}