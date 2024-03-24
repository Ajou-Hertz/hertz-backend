package com.ajou.hertz.unit.domain.user.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import com.ajou.hertz.common.auth.CustomUserDetailsService;
import com.ajou.hertz.common.auth.JwtAccessDeniedHandler;
import com.ajou.hertz.common.auth.JwtAuthenticationEntryPoint;
import com.ajou.hertz.common.auth.JwtAuthenticationFilter;
import com.ajou.hertz.common.auth.JwtExceptionFilter;
import com.ajou.hertz.common.auth.JwtTokenProvider;
import com.ajou.hertz.common.auth.UserPrincipal;
import com.ajou.hertz.common.config.SecurityConfig;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.controller.UserController;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserProfileImageCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;
import com.ajou.hertz.util.ReflectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("[Unit] Controller - User")
@MockBean(JpaMetamodelMappingContext.class)
@Import({
	SecurityConfig.class,
	JwtAccessDeniedHandler.class,
	JwtAuthenticationFilter.class,
	JwtAuthenticationEntryPoint.class,
	JwtExceptionFilter.class,
	JwtTokenProvider.class,
	CustomUserDetailsService.class
})
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@MockBean
	private UserCommandService userCommandService;

	@MockBean
	private UserQueryService userQueryService;

	@MockBean
	private UserProfileImageCommandService userProfileImageCommandService;

	private final MockMvc mvc;

	private final ObjectMapper objectMapper;

	@Autowired
	public UserControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
		this.mvc = mvc;
		this.objectMapper = objectMapper;
	}

	@BeforeTestMethod
	public void securitySetUp() throws Exception {
		given(userQueryService.getDtoById(anyLong())).willReturn(createUserDto());
	}

	@Test
	void 내_정보를_조회한다() throws Exception {
		// given
		long userId = 1L;
		UserDto expectedResult = createUserDto(userId);
		given(userQueryService.getDtoById(userId)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/users/me")
					.header(API_VERSION_HEADER_NAME, 1)
					.with(user(createTestUser(userId)))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()));
		then(userQueryService).should().getDtoById(userId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 이메일이_주어지고_주어진_이메일을_사용_중인_회원의_존재_여부를_조회한다() throws Exception {
		// given
		String email = "test@mail.com";
		boolean expectedResult = true;
		given(userQueryService.existsByEmail(email)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/users/existence")
					.header(API_VERSION_HEADER_NAME, 1)
					.queryParam("email", email)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("isExist").value(expectedResult));
		then(userQueryService).should().existsByEmail(email);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 전화번호가_주어지고_주어진_전화번호를_사용_중인_회원의_이메일을_조회한다() throws Exception {
		// given
		String phone = "01012345678";
		UserDto expectedResult = createUserDto();
		given(userQueryService.getDtoByPhone(phone)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/users/email")
					.header(API_VERSION_HEADER_NAME, 1)
					.queryParam("phone", phone)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("email").value(expectedResult.getEmail()));
		then(userQueryService).should().getDtoByPhone(phone);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 전화번호가_주어지고_주어진_전화번호를_사용_중인_회원의_이메일을_조회한다_전달된_전화번호가_잘못된_형식인_경우_에러가_발생한다() throws Exception {
		// given
		String phone = "12345";

		// when & then
		mvc.perform(
				get("/api/users/email")
					.header(API_VERSION_HEADER_NAME, 1)
					.queryParam("phone", phone)
			)
			.andExpect(status().isUnprocessableEntity());
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
				post("/api/users")
					.header(API_VERSION_HEADER_NAME, 1)
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

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다_이때_전달된_비밀번호가_잘못된_형식인_경우_에러가_발생한다() throws Exception {
		// given
		SignUpRequest signUpRequest = createSignUpRequest(
			"test@mail.com",
			"pass",
			"01012345678"
		);

		// when & then
		mvc.perform(
				post("/api/users")
					.header(API_VERSION_HEADER_NAME, 1)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(signUpRequest))
			)
			.andExpect(status().isUnprocessableEntity());
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다_이때_전달된_전화번호가_잘못된_형식인_경우_에러가_발생한다() throws Exception {
		// given
		SignUpRequest signUpRequest = createSignUpRequest(
			"test@mail.com",
			"1q2w3e4r!",
			"123"
		);

		// when & then
		mvc.perform(
				post("/api/users")
					.header(API_VERSION_HEADER_NAME, 1)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(signUpRequest))
			)
			.andExpect(status().isUnprocessableEntity());
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 주어진_id와_변경할_프로필_이미지로_프로필_이미지를_변경한다() throws Exception {
		// given
		long userId = 1L;
		MockMultipartFile profileImage = new MockMultipartFile(
			"profileImage",
			"test.jpg",
			"image/jpeg",
			"test".getBytes()
		);
		UserDetails userDetails = createTestUser(userId);
		UserDto expectedResult = createUserDto(userId);

		given(userCommandService.updateUserProfileImage(userId, profileImage)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				multipart("/api/users/me/profile-images")
					.file(profileImage)
					.header(API_VERSION_HEADER_NAME, 1)
					.with(user(userDetails))
					.with(request -> {
						request.setMethod("PUT");
						return request;
					})
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.profileImageUrl").value(expectedResult.getProfileImageUrl()));
		then(userCommandService).should().updateUserProfileImage(userId, profileImage);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 주어진_연락수단을_새로운_연락수단으로_변경한다() throws Exception {
		// given
		long userId = 1L;
		String newContactLink = "https://new-contact-link.com";
		UserDto expectedResult = createUserDto(userId);
		UserDetails testUser = createTestUser(userId);
		given(userCommandService.updateContactLink(userId, newContactLink)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				put("/api/users/me/contact-link")
					.header(API_VERSION_HEADER_NAME, 1)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newContactLink))
					.with(user(testUser))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.contactLink").value(expectedResult.getContactLink()));
		then(userCommandService).should().updateContactLink(userId, newContactLink);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userCommandService).shouldHaveNoMoreInteractions();
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(userProfileImageCommandService).shouldHaveNoMoreInteractions();
	}

	private SignUpRequest createSignUpRequest(String email, String password, String phone) throws Exception {
		return ReflectionUtils.createSignUpRequest(
			email,
			password,
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			phone
		);
	}

	private SignUpRequest createSignUpRequest() throws Exception {
		return createSignUpRequest(
			"test@test.com",
			"1q2w3e4r!",
			"01012345678"
		);
	}

	private UserDto createUserDto(long id) throws Exception {
		return ReflectionUtils.createUserDto(
			id,
			Set.of(RoleType.USER),
			"test@mail.com",
			"$2a$abc123",
			"kakao-user-id",
			"https://user-default-profile-image",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678",
			"https://contact-link",
			LocalDateTime.of(2024, 1, 1, 0, 0)
		);
	}

	private UserDto createUserDto() throws Exception {
		return createUserDto(1L);
	}

	private UserDetails createTestUser(Long userId) throws Exception {
		return new UserPrincipal(createUserDto(userId));
	}
}