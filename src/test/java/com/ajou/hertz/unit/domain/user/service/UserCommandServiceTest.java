package com.ajou.hertz.unit.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.common.kakao.dto.response.KakaoUserInfoResponse;
import com.ajou.hertz.common.properties.HertzProperties;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.exception.UserEmailDuplicationException;
import com.ajou.hertz.domain.user.exception.UserKakaoUidDuplicationException;
import com.ajou.hertz.domain.user.exception.UserNotFoundByIdException;
import com.ajou.hertz.domain.user.exception.UserPhoneDuplicationException;
import com.ajou.hertz.domain.user.repository.UserRepository;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserProfileImageCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Service(Command) - User")
@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

	@InjectMocks
	private UserCommandService sut;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private HertzProperties hertzProperties;

	@Mock
	private FileService fileService;

	@Mock
	private UserProfileImageCommandService userProfileImageCommandService;

	@BeforeTestMethod
	public void setUp() {
		given(hertzProperties.userDefaultProfileImageUrl()).willReturn("https://user-default-profile-image");
	}

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다() throws Exception {
		// given
		long userId = 1L;
		SignUpRequest signUpRequest = createSignUpRequest();
		String passwordEncoded = "$2a$abc123";
		User expectedResult = createUser(userId, passwordEncoded, "12345");
		given(userQueryService.existsByEmail(signUpRequest.getEmail())).willReturn(false);
		given(userQueryService.existsByPhone(signUpRequest.getPhone())).willReturn(false);
		given(passwordEncoder.encode(signUpRequest.getPassword())).willReturn(passwordEncoded);
		given(userRepository.save(any(User.class))).willReturn(expectedResult);

		// when
		UserDto actualResult = sut.createNewUser(signUpRequest);

		// then
		then(userQueryService).should().existsByEmail(signUpRequest.getEmail());
		then(userQueryService).should().existsByPhone(signUpRequest.getPhone());
		then(passwordEncoder).should().encode(signUpRequest.getPassword());
		then(userRepository).should().save(any(User.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getId()).isEqualTo(expectedResult.getId());
	}

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다_이미_사용_중인_이메일이라면_예외가_발생한다() throws Exception {
		// given
		String email = "test@test.com";
		SignUpRequest signUpRequest = createSignUpRequest(email, "01012345678");
		given(userQueryService.existsByEmail(email)).willReturn(true);

		// when
		Throwable t = catchThrowable(() -> sut.createNewUser(signUpRequest));

		// then
		then(userQueryService).should().existsByEmail(email);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserEmailDuplicationException.class);
	}

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다_이미_사용_중인_전화번호라면_예외가_발생한다() throws Exception {
		// given
		String phone = "01012345678";
		SignUpRequest signUpRequest = createSignUpRequest(phone, phone);
		given(userQueryService.existsByEmail(signUpRequest.getEmail())).willReturn(false);
		given(userQueryService.existsByPhone(phone)).willReturn(true);

		// when
		Throwable t = catchThrowable(() -> sut.createNewUser(signUpRequest));

		// then
		then(userQueryService).should().existsByEmail(signUpRequest.getEmail());
		then(userQueryService).should().existsByPhone(phone);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserPhoneDuplicationException.class);
	}

	@MethodSource("testDataForCreateNewUserWithKakao")
	@ParameterizedTest
	void 주어진_카카오_유저_정보로_신규_회원을_등록한다(KakaoUserInfoResponse kakaoUserInfo, User expectedResult) throws Exception {
		// given
		given(userQueryService.existsByEmail(kakaoUserInfo.email())).willReturn(false);
		given(userQueryService.existsByPhone(kakaoUserInfo.getKoreanFormatPhoneNumber())).willReturn(false);
		given(userQueryService.existsByKakaoUid(kakaoUserInfo.id())).willReturn(false);
		given(passwordEncoder.encode(anyString())).willReturn(expectedResult.getPassword());
		given(userRepository.save(any(User.class))).willReturn(expectedResult);

		// when
		UserDto actualResult = sut.createNewUserWithKakao(kakaoUserInfo);

		// then
		then(userQueryService).should().existsByEmail(kakaoUserInfo.email());
		then(userQueryService).should().existsByPhone(kakaoUserInfo.getKoreanFormatPhoneNumber());
		then(userQueryService).should().existsByKakaoUid(kakaoUserInfo.id());
		then(passwordEncoder).should().encode(anyString());
		then(userRepository).should().save(any(User.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult)
			.hasFieldOrPropertyWithValue("id", expectedResult.getId())
			.hasFieldOrPropertyWithValue("kakaoUid", expectedResult.getKakaoUid())
			.hasFieldOrPropertyWithValue("password", expectedResult.getPassword());
	}

	static Stream<Arguments> testDataForCreateNewUserWithKakao() throws Exception {
		return Stream.of(
			arguments(createKakaoUserInfoResponse("male"), createUser(1L, "$2a$abc123", "12345", Gender.MALE)),
			arguments(createKakaoUserInfoResponse("female"), createUser(1L, "$2a$abc123", "12345", Gender.FEMALE)),
			arguments(createKakaoUserInfoResponse(""), createUser(1L, "$2a$abc123", "12345", null)),
			arguments(createKakaoUserInfoResponse(null), createUser(1L, "$2a$abc123", "12345", null))
		);
	}

	@Test
	void 주어진_카카오_유저_정보로_신규_회원을_등록한다_이미_가입한_카카오_계정이라면_예외가_발생한다() throws Exception {
		// given
		KakaoUserInfoResponse kakaoUserInfo = createKakaoUserInfoResponse();
		given(userQueryService.existsByEmail(kakaoUserInfo.email())).willReturn(false);
		given(userQueryService.existsByPhone(kakaoUserInfo.getKoreanFormatPhoneNumber())).willReturn(false);
		given(userQueryService.existsByKakaoUid(kakaoUserInfo.id())).willReturn(true);

		// when
		Throwable t = catchThrowable(() -> sut.createNewUserWithKakao(kakaoUserInfo));

		// then
		then(userQueryService).should().existsByEmail(kakaoUserInfo.email());
		then(userQueryService).should().existsByPhone(kakaoUserInfo.getKoreanFormatPhoneNumber());
		then(userQueryService).should().existsByKakaoUid(kakaoUserInfo.id());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserKakaoUidDuplicationException.class);
	}

	@Test
	void 주어진_유저_ID와_이미지_URL로_유저의_프로필_이미지를_업데이트한다() throws Exception {
		// Given
		Long userId = 1L;
		User user = createUser(userId, "password", "kakaoUid");
		String newProfileImageUrl = "https://new-profile-image-url";

		MultipartFile profileImage = new MockMultipartFile("file", "test.jpg", "image/jpeg",
			"test image content".getBytes());

		given(userQueryService.getById(userId)).willReturn(user);
		given(userProfileImageCommandService.updateProfileImage(user, profileImage)).willReturn(
			newProfileImageUrl);

		// When
		UserDto result = sut.updateUserProfileImage(userId, profileImage);

		// Then
		then(userQueryService).should().getById(userId);
		then(userProfileImageCommandService).should().updateProfileImage(user, profileImage);
		assertThat(result.getProfileImageUrl()).isEqualTo(newProfileImageUrl);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 주어진_유저_ID와_이미지_URL로_유저의_프로필_이미지를_업데이트한다_존재하지_않는_유저라면_예외가_발생한다() throws Exception {
		// Given
		Long userId = 1L;
		MultipartFile profileImage = new MockMultipartFile("file", "test.jpg", "image/jpeg",
			"test image content".getBytes());

		given(userQueryService.getById(userId)).willThrow(UserNotFoundByIdException.class);

		// When
		Throwable t = catchThrowable(() -> sut.updateUserProfileImage(userId, profileImage));

		// Then
		then(userQueryService).should().getById(userId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserNotFoundByIdException.class);
	}

	@Test
	void 주어진_유저_ID와_연락_수단으로_연락_수단을_변경한다() throws Exception {
		// given
		Long userId = 1L;
		String contactLink = "https://new-contactLink";
		User user = createUser(userId, "$2a$abc123", "12345");
		given(userQueryService.getById(userId)).willReturn(user);

		// when
		UserDto updatedUserDto = sut.updateContactLink(userId, contactLink);

		// then
		then(userQueryService).should().getById(userId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(updatedUserDto.getContactLink()).isEqualTo(contactLink);
	}

	@Test
	void 주어진_유저_ID와_연락_수단으로_연락_수단을_변경한다_존재하지_않는_유저라면_예외가_발생한다() throws Exception {
		// given
		Long userId = 1L;
		String contactLink = "https://new-contactLink";
		given(userQueryService.getById(userId)).willThrow(UserNotFoundByIdException.class);

		// when
		Throwable t = catchThrowable(() -> sut.updateContactLink(userId, contactLink));

		// then
		then(userQueryService).should().getById(userId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserNotFoundByIdException.class);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(userRepository).shouldHaveNoMoreInteractions();
		then(passwordEncoder).shouldHaveNoMoreInteractions();
		then(fileService).shouldHaveNoMoreInteractions();
		then(userProfileImageCommandService).shouldHaveNoMoreInteractions();
	}

	private static User createUser(Long id, String password, String kakaoUid, Gender gender) throws Exception {
		return ReflectionUtils.createUser(
			id,
			Set.of(RoleType.USER),
			"test@test.com",
			password,
			kakaoUid,
			"https://user-default-profile-image-url",
			LocalDate.of(2024, 1, 1),
			gender,
			"010-1234-5678",
			"https://contactLink"
		);
	}

	private static User createUser(Long id, String password, String kakaoUid) throws Exception {
		return createUser(id, password, kakaoUid, Gender.ETC);
	}

	private SignUpRequest createSignUpRequest(String email, String phone) throws Exception {
		return ReflectionUtils.createSignUpRequest(
			email,
			"1q2w3e4r!",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			phone
		);
	}

	private SignUpRequest createSignUpRequest() throws Exception {
		return createSignUpRequest("test@test.com", "01012345678");
	}

	private static KakaoUserInfoResponse createKakaoUserInfoResponse(String gender) {
		return new KakaoUserInfoResponse(
			"12345",
			new KakaoUserInfoResponse.KakaoAccount(
				true,
				new KakaoUserInfoResponse.KakaoAccount.Profile(
					"https://profile-image-url",
					"https://thumbnail-image-url",
					true
				),
				true,
				true,
				true,
				true,
				"test@mail.com",
				true,
				true,
				"01012345678",
				true,
				true,
				null,
				true,
				null,
				true,
				true,
				gender
			)
		);
	}

	private static KakaoUserInfoResponse createKakaoUserInfoResponse() {
		return createKakaoUserInfoResponse("male");
	}
}