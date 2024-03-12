package com.ajou.hertz.unit.common.kakao.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import com.ajou.hertz.common.auth.JwtTokenProvider;
import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.dto.request.KakaoLoginRequest;
import com.ajou.hertz.common.kakao.client.KakaoAuthClient;
import com.ajou.hertz.common.kakao.client.KakaoClient;
import com.ajou.hertz.common.kakao.dto.request.IssueKakaoAccessTokenRequest;
import com.ajou.hertz.common.kakao.dto.response.KakaoTokenResponse;
import com.ajou.hertz.common.kakao.dto.response.KakaoUserInfoResponse;
import com.ajou.hertz.common.kakao.service.KakaoService;
import com.ajou.hertz.common.properties.KakaoProperties;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Service - Kakao")
@ExtendWith(MockitoExtension.class)
class KakaoServiceTest {

	@InjectMocks
	private KakaoService sut;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private UserCommandService userCommandService;

	@Mock
	private KakaoClient kakaoClient;

	@Mock
	private KakaoAuthClient kakaoAuthClient;

	@Mock
	private KakaoProperties kakaoProperties;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@BeforeTestMethod
	public void setUp() {
		given(kakaoProperties.restApiKey()).willReturn("kakao-rest-api-key");
		given(kakaoProperties.clientSecret()).willReturn("kakao-client-secret");
	}

	@Test
	void 인가_코드와_리다이렉트_주소가_주어지고_주어진_정보로_카카오_로그인을_진행한다() throws Exception {
		// given
		KakaoLoginRequest kakaoLoginRequest = createKakaoLoginRequest();
		KakaoTokenResponse kakaoTokenResponse = createKakaoTokenResponse();
		String authorizationHeaderToGettingKakaoUserInfo = "Bearer " + kakaoTokenResponse.getAccessToken();
		KakaoUserInfoResponse kakaoUserInfoResponse = createKakaoUserInfoResponse();
		UserDto userDto = createUserDto();
		JwtTokenInfoDto expectedResult = createJwtTokenInfoDto();
		given(kakaoAuthClient.issueAccessToken(any(IssueKakaoAccessTokenRequest.class))).willReturn(kakaoTokenResponse);
		given(
			kakaoClient.getUserInfo(authorizationHeaderToGettingKakaoUserInfo, true)
		).willReturn(kakaoUserInfoResponse);
		given(userQueryService.findDtoByKakaoUid(kakaoUserInfoResponse.id())).willReturn(Optional.of(userDto));
		given(jwtTokenProvider.createAccessToken(userDto)).willReturn(expectedResult);

		// when
		JwtTokenInfoDto actualResult = sut.login(kakaoLoginRequest);

		// then
		then(kakaoProperties).should().restApiKey();
		then(kakaoProperties).should().clientSecret();
		then(kakaoAuthClient).should().issueAccessToken(any(IssueKakaoAccessTokenRequest.class));
		then(kakaoClient).should().getUserInfo(authorizationHeaderToGettingKakaoUserInfo, true);
		then(userQueryService).should().findDtoByKakaoUid(kakaoUserInfoResponse.id());
		then(jwtTokenProvider).should().createAccessToken(userDto);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult)
			.hasFieldOrPropertyWithValue("token", expectedResult.token());
	}

	@Test
	void 인가_코드와_리다이렉트_주소가_주어지고_주어진_정보로_카카오_로그인을_진행한다_만약_신규_회원이라면_회원가입한다() throws Exception {
		// given
		KakaoLoginRequest kakaoLoginRequest = createKakaoLoginRequest();
		KakaoTokenResponse kakaoTokenResponse = createKakaoTokenResponse();
		String authorizationHeaderToGettingKakaoUserInfo = "Bearer " + kakaoTokenResponse.getAccessToken();
		KakaoUserInfoResponse kakaoUserInfoResponse = createKakaoUserInfoResponse();
		UserDto userDto = createUserDto();
		JwtTokenInfoDto expectedResult = createJwtTokenInfoDto();
		given(kakaoAuthClient.issueAccessToken(any(IssueKakaoAccessTokenRequest.class))).willReturn(kakaoTokenResponse);
		given(
			kakaoClient.getUserInfo(authorizationHeaderToGettingKakaoUserInfo, true)
		).willReturn(kakaoUserInfoResponse);
		given(userQueryService.findDtoByKakaoUid(kakaoUserInfoResponse.id())).willReturn(Optional.empty());
		given(userCommandService.createNewUserWithKakao(kakaoUserInfoResponse)).willReturn(userDto);
		given(jwtTokenProvider.createAccessToken(userDto)).willReturn(expectedResult);

		// when
		JwtTokenInfoDto actualResult = sut.login(kakaoLoginRequest);

		// then
		then(kakaoProperties).should().restApiKey();
		then(kakaoProperties).should().clientSecret();
		then(kakaoAuthClient).should().issueAccessToken(any(IssueKakaoAccessTokenRequest.class));
		then(kakaoClient).should().getUserInfo(authorizationHeaderToGettingKakaoUserInfo, true);
		then(userQueryService).should().findDtoByKakaoUid(kakaoUserInfoResponse.id());
		then(userCommandService).should().createNewUserWithKakao(kakaoUserInfoResponse);
		then(jwtTokenProvider).should().createAccessToken(userDto);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult)
			.hasFieldOrPropertyWithValue("token", expectedResult.token());
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(userCommandService).shouldHaveNoMoreInteractions();
		then(kakaoClient).shouldHaveNoMoreInteractions();
		then(kakaoAuthClient).shouldHaveNoMoreInteractions();
		then(kakaoProperties).shouldHaveNoMoreInteractions();
		then(jwtTokenProvider).shouldHaveNoMoreInteractions();
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
			"https://contack-link",
			LocalDateTime.of(2024, 1, 1, 0, 0)
		);
	}

	private UserDto createUserDto() throws Exception {
		return createUserDto(1L);
	}

	private static KakaoLoginRequest createKakaoLoginRequest() throws Exception {
		return ReflectionUtils.createKakaoLoginRequest(
			"authorization-code",
			"https://redirect-uri"
		);
	}

	private static KakaoTokenResponse createKakaoTokenResponse() throws Exception {
		return ReflectionUtils.createKakaoTokenResponse("bearer", "access-token", 43199, "refresh-token", 5184000);
	}

	private static KakaoUserInfoResponse createKakaoUserInfoResponse() {
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
				"male"
			)
		);
	}

	private static JwtTokenInfoDto createJwtTokenInfoDto() {
		return new JwtTokenInfoDto(
			"access-token",
			LocalDateTime.of(2024, 1, 1, 0, 0)
		);
	}
}