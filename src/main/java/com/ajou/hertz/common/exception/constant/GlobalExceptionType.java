package com.ajou.hertz.common.exception.constant;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ajou.hertz.common.logger.Logger;

import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error code 목록
 *
 * <ul>
 *     <li>120X: Validation 관련 예외</li>
 *     <li>1300 ~ 1349: API/Controller 관련 예외</li>
 * </ul>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum GlobalExceptionType {

	UNHANDLED(1000, "알 수 없는 서버 에러가 발생했습니다.", null),

	/**
	 * Validation Exception
	 */
	METHOD_ARGUMENT_NOT_VALID(1200, "요청 데이터가 잘못되었습니다.", MethodArgumentNotValidException.class),
	CONSTRAINT_VIOLATION(1200, "요청 데이터가 잘못되었습니다.", ConstraintViolationException.class),

	/**
	 * Spring MVC Default Exception
	 */
	HTTP_REQUEST_METHOD_NOT_SUPPORTED(1300, "지원하지 않는 요청 방식입니다.", HttpRequestMethodNotSupportedException.class),
	HTTP_MEDIA_TYPE_NOT_SUPPORTED(1301, "지원하지 않는 요청 데이터 타입입니다.", HttpMediaTypeNotSupportedException.class),
	HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(1302, "요청한 데이터 타입으로 응답을 만들어 낼 수 없습니다.", HttpMediaTypeNotAcceptableException.class),
	MISSING_PATH_VARIABLE(1303, "필요로 하는 path variable이 누락 되었습니다.", MissingPathVariableException.class),
	MISSING_SERVLET_REQUEST_PARAMETER(1304, "필요로 하는 request parameter가 누락 되었습니다.",
		MissingServletRequestParameterException.class),
	MISSING_REQUEST_HEADER(1305, "필요로 하는 request header가 누락 되었습니다.", MissingRequestHeaderException.class),
	SERVLET_REQUEST_BINDING(1306, "복구 불가능한 fatal binding exception이 발생했습니다.", ServletRequestBindingException.class),
	CONVERSION_NOT_SUPPORTED(1307, "Bean property에 대해 적절한 editor 또는 convertor를 찾을 수 없습니다.",
		ConversionNotSupportedException.class),
	TYPE_MISMATCH(1308, "Bean property를 설정하던 중 type mismatch로 인한 예외가 발생했습니다.", TypeMismatchException.class),
	HTTP_MESSAGE_NOT_READABLE(1309, "읽을 수 없는 요청입니다. 요청 정보가 잘못되지는 않았는지 확인해주세요.", HttpMessageNotReadableException.class),
	HTTP_MESSAGE_NOT_WRITABLE(1310, "응답 데이터를 생성할 수 없습니다.", HttpMessageNotWritableException.class),
	MISSING_SERVLET_REQUEST_PART(1311, "multipart/form-data 형식의 요청 데이터에 대해 일부가 손실되거나 누락되었습니다.",
		MissingServletRequestPartException.class),
	NO_HANDLER_FOUND(1312, "알 수 없는 에러가 발생했으며, 에러를 처리할 handler를 찾지 못했습니다.", NoHandlerFoundException.class),
	ASYNC_REQUEST_TIMEOUT(1313, "요청에 대한 응답 시간이 초과되었습니다.", AsyncRequestTimeoutException.class),
	BIND(1314, "Request binding에 실패했습니다. 요청 데이터를 확인해주세요.", BindException.class),
	;

	private final Integer code;
	private final String message;
	private final Class<? extends Exception> type;

	public static Optional<GlobalExceptionType> from(Class<? extends Exception> classType) {
		Optional<GlobalExceptionType> exceptionType = Arrays.stream(values())
			.filter(ex -> ex.getType() != null && ex.getType().isAssignableFrom(classType))
			.findFirst();
		if (exceptionType.isEmpty()) {
			Logger.error(String.format("정의되지 않은 exception이 발생하였습니다. Type of exception=%s", classType));
		}
		return exceptionType;
	}
}
