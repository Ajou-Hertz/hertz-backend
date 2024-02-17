package com.ajou.hertz.common.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.common.auth.JwtTokenProvider;
import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.dto.request.LoginRequest;
import com.ajou.hertz.common.auth.exception.PasswordMismatchException;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.service.UserQueryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

	private final UserQueryService userQueryService;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 로그인을 진행한다.
	 *
	 * @param loginRequest 로그인에 필요한 정보(이메일, 비밀번호)
	 * @return 로그인 성공 시 발급한 access token 정보
	 * @throws PasswordMismatchException 비밀번호가 일치하지 않는 경우
	 */
	public JwtTokenInfoDto login(LoginRequest loginRequest) {
		UserDto user = userQueryService.getDtoByEmail(loginRequest.getEmail());
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new PasswordMismatchException();
		}
		return jwtTokenProvider.createAccessToken(user);
	}
}
