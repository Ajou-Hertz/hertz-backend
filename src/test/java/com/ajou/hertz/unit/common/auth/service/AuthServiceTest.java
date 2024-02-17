package com.ajou.hertz.unit.common.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ajou.hertz.common.auth.JwtTokenProvider;
import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.dto.request.LoginRequest;
import com.ajou.hertz.common.auth.exception.PasswordMismatchException;
import com.ajou.hertz.common.auth.service.AuthService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.service.UserQueryService;

@DisplayName("[Unit] Service - Auth")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	private AuthService sut;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@Test
	void 이메일과_비밀번호가_주어지고_로그인을_진행한다() throws Exception {
		// given
		LoginRequest loginRequest = createLoginRequest();
		UserDto userDto = createUserDto();
		JwtTokenInfoDto expectedResult = createJwtTokenInfoDto();
		given(userQueryService.getDtoByEmail(loginRequest.getEmail())).willReturn(userDto);
		given(passwordEncoder.matches(loginRequest.getPassword(), userDto.getPassword())).willReturn(true);
		given(jwtTokenProvider.createAccessToken(userDto)).willReturn(expectedResult);

		// when
		JwtTokenInfoDto actualResult = sut.login(loginRequest);

		// then
		then(userQueryService).should().getDtoByEmail(loginRequest.getEmail());
		then(passwordEncoder).should().matches(loginRequest.getPassword(), userDto.getPassword());
		then(jwtTokenProvider).should().createAccessToken(userDto);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult)
			.hasFieldOrPropertyWithValue("token", expectedResult.token())
			.hasFieldOrPropertyWithValue("expiresAt", expectedResult.expiresAt());
	}

	@Test
	void 이메일과_비밀번호가_주어지고_로그인을_진행한다_이때_비밀번호가_일치하지_않으면_예외가_발생한다() throws Exception {
		// given
		LoginRequest loginRequest = createLoginRequest();
		UserDto userDto = createUserDto();
		given(userQueryService.getDtoByEmail(loginRequest.getEmail())).willReturn(userDto);
		given(passwordEncoder.matches(loginRequest.getPassword(), userDto.getPassword())).willReturn(false);

		// when
		Throwable t = catchThrowable(() -> sut.login(loginRequest));

		// then
		then(userQueryService).should().getDtoByEmail(loginRequest.getEmail());
		then(passwordEncoder).should().matches(loginRequest.getPassword(), userDto.getPassword());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(PasswordMismatchException.class);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(passwordEncoder).shouldHaveNoMoreInteractions();
		then(jwtTokenProvider).shouldHaveNoMoreInteractions();
	}

	private LoginRequest createLoginRequest() throws Exception {
		Constructor<LoginRequest> loginRequestConstructor =
			LoginRequest.class.getDeclaredConstructor(String.class, String.class);
		loginRequestConstructor.setAccessible(true);
		return loginRequestConstructor.newInstance(
			"test@mail.com",
			"encoded-password"
		);
	}

	private JwtTokenInfoDto createJwtTokenInfoDto() {
		return new JwtTokenInfoDto(
			"access-token",
			LocalDateTime.of(2024, 1, 1, 0, 0)
		);
	}

	private UserDto createUserDto(long id) throws Exception {
		Constructor<UserDto> userResponseConstructor = UserDto.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			LocalDate.class, Gender.class, String.class, String.class
		);
		userResponseConstructor.setAccessible(true);
		return userResponseConstructor.newInstance(
			id,
			Set.of(RoleType.USER),
			"test@mail.com",
			"$2a$abc123",
			"kakao-user-id",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678",
			"https://contack-link"
		);
	}

	private UserDto createUserDto() throws Exception {
		return createUserDto(1L);
	}
}