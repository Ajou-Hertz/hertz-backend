package com.ajou.hertz.unit.domain.user.controller;

import static com.ajou.hertz.global.common.constant.GlobalConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ajou.hertz.config.ControllerTestConfig;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.controller.UserControllerV1;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("[Unit] Controller - User")
@Import(ControllerTestConfig.class)
@WebMvcTest(controllers = UserControllerV1.class)
class UserControllerV1Test {

	@MockBean
	private UserCommandService userCommandService;

	@MockBean
	private UserQueryService userQueryService;

	private final MockMvc mvc;

	private final ObjectMapper objectMapper;

	@Autowired
	public UserControllerV1Test(MockMvc mvc, ObjectMapper objectMapper) {
		this.mvc = mvc;
		this.objectMapper = objectMapper;
	}

	@Test
	void 이메일이_주어지고_주어진_이메일을_사용_중인_회원의_존재_여부를_조회한다() throws Exception {
	    // given
		String email = "test@mail.com";
		boolean expectedResult = true;
		given(userQueryService.existsByEmail(email)).willReturn(expectedResult);

	    // when & then
		mvc.perform(
				get("/v1/users/existence")
					.header(API_MINOR_VERSION_HEADER_NAME, 1)
					.param("email", email)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("isExist").value(expectedResult));
		then(userQueryService).should().existsByEmail(email);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다() throws Exception {
		// given
		long userId = 1L;
		SignUpRequest signUpRequest = createSignUpRequest();
		UserDto expectedResult = createUserDto(userId);
		given(userCommandService.createNewUser(any(SignUpRequest.class))).willReturn(expectedResult);

		// when & then
		mvc.perform(
				post("/v1/users")
					.header(API_MINOR_VERSION_HEADER_NAME, 1)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(signUpRequest))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.email").value(expectedResult.getEmail()))
			.andExpect(jsonPath("$.birth").value(expectedResult.getBirth().toString()))
			.andExpect(jsonPath("$.gender").value(expectedResult.getGender().name()))
			.andExpect(jsonPath("$.contactLink").value(expectedResult.getContactLink()));
		then(userCommandService).should().createNewUser(any(SignUpRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userCommandService).shouldHaveNoMoreInteractions();
		then(userQueryService).shouldHaveNoMoreInteractions();
	}

	private SignUpRequest createSignUpRequest() throws Exception {
		Constructor<SignUpRequest> signUpRequestConstructor = SignUpRequest.class.getDeclaredConstructor(
			String.class, String.class, LocalDate.class, Gender.class, String.class
		);
		signUpRequestConstructor.setAccessible(true);
		return signUpRequestConstructor.newInstance(
			"test@test.com",
			"1q2w3e4r!",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678"
		);
	}

	private UserDto createUserDto(long id) throws Exception {
		Constructor<UserDto> userResponseConstructor = UserDto.class.getDeclaredConstructor(
			Long.class, String.class, String.class, String.class,
			LocalDate.class, Gender.class, String.class, String.class
		);
		userResponseConstructor.setAccessible(true);
		return userResponseConstructor.newInstance(
			id,
			"test@mail.com",
			"$2a$abc123",
			"kakao-user-id",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678",
			"https://contack-link"
		);
	}
}