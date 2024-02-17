package com.ajou.hertz.common.auth;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ajou.hertz.common.auth.exception.TokenValidateException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;
import com.ajou.hertz.common.exception.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <code>JwtAuthenticationFilter</code>에서 발생하는 에러를 처리하기 위한 filter
 *
 * @see JwtAuthenticationFilter
 */
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (TokenValidateException ex) {
			setErrorResponse(CustomExceptionType.TOKEN_VALIDATE, response);
		}
	}

	/**
	 * Exception 정보를 입력받아 응답할 error response를 설정한다.
	 *
	 * @param exceptionType exception type
	 * @param response      HttpServletResponse 객체
	 */
	private void setErrorResponse(
		CustomExceptionType exceptionType,
		HttpServletResponse response
	) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=UTF-8");
		ErrorResponse errorResponse = new ErrorResponse(exceptionType.getCode(), exceptionType.getMessage());
		new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
	}
}
