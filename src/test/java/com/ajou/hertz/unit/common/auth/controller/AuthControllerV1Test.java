package com.ajou.hertz.unit.common.auth.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ajou.hertz.common.auth.controller.AuthControllerV1;
import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.dto.request.LoginRequest;
import com.ajou.hertz.common.auth.service.AuthService;
import com.ajou.hertz.config.ControllerTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("[Unit] Controller - Auth(V1)")
@Import(ControllerTestConfig.class)
@WebMvcTest(controllers = AuthControllerV1.class)
class AuthControllerV1Test {

	@MockBean
	private AuthService authService;

	private final MockMvc mvc;

	private final ObjectMapper objectMapper;

	@Autowired
	public AuthControllerV1Test(MockMvc mvc, ObjectMapper objectMapper) {
		this.mvc = mvc;
		this.objectMapper = objectMapper;
	}

	@Test
	void 로그인_정보가_주어지고_로그인을_진행한다() throws Exception {
		// given
		LoginRequest loginRequest = createLoginRequest();
		JwtTokenInfoDto expectedResult = createJwtTokenInfoDto();
		given(authService.login(any(LoginRequest.class))).willReturn(expectedResult);

		// when & then
		mvc.perform(
				post("/v1/auth/login")
					.header(API_MINOR_VERSION_HEADER_NAME, 1)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(loginRequest))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").value(expectedResult.token()));
		then(authService).should().login(any(LoginRequest.class));
		then(authService).shouldHaveNoMoreInteractions();
	}

	private LoginRequest createLoginRequest() throws Exception {
		Constructor<LoginRequest> loginRequestConstructor =
			LoginRequest.class.getDeclaredConstructor(String.class, String.class);
		loginRequestConstructor.setAccessible(true);
		return loginRequestConstructor.newInstance(
			"test@mail.com",
			"1q2w3e4r!"
		);
	}

	private JwtTokenInfoDto createJwtTokenInfoDto() {
		return new JwtTokenInfoDto(
			"access-token",
			LocalDateTime.of(2024, 1, 1, 0, 0)
		);
	}
}