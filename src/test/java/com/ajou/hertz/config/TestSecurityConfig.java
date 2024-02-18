package com.ajou.hertz.config;

import static org.mockito.BDDMockito.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import com.ajou.hertz.common.auth.CustomUserDetailsService;
import com.ajou.hertz.common.auth.JwtAccessDeniedHandler;
import com.ajou.hertz.common.auth.JwtAuthenticationEntryPoint;
import com.ajou.hertz.common.auth.JwtAuthenticationFilter;
import com.ajou.hertz.common.auth.JwtExceptionFilter;
import com.ajou.hertz.common.auth.JwtTokenProvider;
import com.ajou.hertz.common.config.SecurityConfig;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.service.UserQueryService;

@Import({
	SecurityConfig.class,
	JwtAccessDeniedHandler.class,
	JwtAuthenticationFilter.class,
	JwtAuthenticationEntryPoint.class,
	JwtExceptionFilter.class,
	JwtTokenProvider.class,
	CustomUserDetailsService.class
})
@TestConfiguration
public class TestSecurityConfig {

	@MockBean
	private UserQueryService userQueryService;

	@BeforeTestMethod
	public void securitySetUp() throws Exception {
		given(userQueryService.getDtoById(anyLong())).willReturn(createUserDto());
	}

	private UserDto createUserDto() throws Exception {
		Constructor<UserDto> userResponseConstructor = UserDto.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			String.class, LocalDate.class, Gender.class, String.class, String.class
		);
		userResponseConstructor.setAccessible(true);
		return userResponseConstructor.newInstance(
			1L,
			Set.of(RoleType.USER),
			"test@mail.com",
			"$2a$abc123",
			"kakao-user-id",
			"https://user-default-profile-image",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678",
			"https://contack-link"
		);
	}
}
