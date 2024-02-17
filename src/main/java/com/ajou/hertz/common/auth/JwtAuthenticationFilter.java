package com.ajou.hertz.common.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String TOKEN_TYPE_BEARER_PREFIX = "Bearer ";

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 모든 요청마다 작동하여, jwt access token을 확인한다.
	 * 유효한 token이 있는 경우 token을 parsing해서 사용자 정보를 읽고 SecurityContext에 사용자 정보를 저장한다.
	 *
	 * @param request     request 객체
	 * @param response    response 객체
	 * @param filterChain FilterChain 객체
	 */
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String accessToken = getAccessToken(request);

		if (StringUtils.hasText(accessToken)) {
			try {
				jwtTokenProvider.validateToken(accessToken);
				Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception ignored) {
				// 인증 권한 설정 중 에러가 발생하면 권한을 부여하지 않고 다음 단계로 진행
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * Request의 header에서 token을 읽어온다.
	 *
	 * @param request Request 객체
	 * @return Header에서 추출한 token
	 */
	public String getAccessToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_TYPE_BEARER_PREFIX)) {
			return null;
		}
		return authorizationHeader.substring(TOKEN_TYPE_BEARER_PREFIX.length());
	}
}
