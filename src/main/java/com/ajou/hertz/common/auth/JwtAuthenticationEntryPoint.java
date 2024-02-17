package com.ajou.hertz.common.auth;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;
import com.ajou.hertz.common.exception.dto.response.ErrorResponse;
import com.ajou.hertz.common.exception.util.ExceptionUtils;
import com.ajou.hertz.common.logger.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * 인증이 필요한 endpoint에 대해 인증되지 않았을 때 동작하는 handler.
	 *
	 * @param request                 that resulted in an <code>AuthenticationException</code>
	 * @param response                so that the user agent can begin authentication
	 * @param authenticationException that caused the invocation
	 * @throws IOException if an input or output exception occurred
	 */
	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authenticationException
	) throws IOException {
		Logger.warn(String.format(
			"JwtAuthenticationEntryPoint.commence() ex=%s",
			ExceptionUtils.getExceptionStackTrace(authenticationException)
		));

		response.setStatus(UNAUTHORIZED.value());
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(
			new ObjectMapper().writeValueAsString(
				new ErrorResponse(
					CustomExceptionType.ACCESS_DENIED.getCode(),
					CustomExceptionType.ACCESS_DENIED.getMessage()
				)
			)
		);
	}
}
