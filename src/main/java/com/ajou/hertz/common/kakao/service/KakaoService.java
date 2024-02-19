package com.ajou.hertz.common.kakao.service;

import org.springframework.stereotype.Service;

import com.ajou.hertz.common.auth.JwtTokenProvider;
import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.dto.request.KakaoLoginRequest;
import com.ajou.hertz.common.kakao.client.KakaoAuthClient;
import com.ajou.hertz.common.kakao.client.KakaoClient;
import com.ajou.hertz.common.kakao.dto.request.IssueKakaoAccessTokenRequest;
import com.ajou.hertz.common.kakao.dto.response.KakaoTokenResponse;
import com.ajou.hertz.common.kakao.dto.response.KakaoUserInfoResponse;
import com.ajou.hertz.common.kakao.exception.KakaoClientException;
import com.ajou.hertz.common.properties.KakaoProperties;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.exception.UserEmailDuplicationException;
import com.ajou.hertz.domain.user.exception.UserPhoneDuplicationException;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KakaoService {

	private final UserQueryService userQueryService;
	private final UserCommandService userCommandService;
	private final KakaoClient kakaoClient;
	private final KakaoAuthClient kakaoAuthClient;
	private final KakaoProperties kakaoProperties;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * <p>카카오에서 발급 받은 인가 코드로 유저 정보를 조회한 후, 로그인한다.
	 * <p>만약 신규 회원이라면 신규 회원을 등록한다.
	 *
	 * @param kakaoLoginRequest 카카오 로그인을 위한 정보(인가코드 등)
	 * @return 로그인 성공 시 발급한 access token 정보
	 * @throws UserEmailDuplicationException 전달된 이메일이 중복된 이메일인 경우. 즉, 다른 사용자가 이미 같은 이메일을 사용하고 있는 경우.
	 * @throws UserPhoneDuplicationException 전달된 전화번호가 중복된 전화번호인 경우. 즉, 다른 사용자가 이미 같은 전화번호를 사용하고 있는 경우.
	 * @throws KakaoClientException 카카오 서버와의 통신 중 오류가 발생한 경우.
	 */
	public JwtTokenInfoDto login(KakaoLoginRequest kakaoLoginRequest) {
		String kakaoAccessToken = issueAccessToken(kakaoLoginRequest);
		KakaoUserInfoResponse userInfo = kakaoClient.getUserInfo("Bearer " + kakaoAccessToken, true);

		// 기존 존재하는 회원이라면 조회, 신규 회원이라면 신규 회원 등록 로직 수행
		UserDto user = userQueryService
			.findDtoByKakaoUid(userInfo.id())
			.orElseGet(() -> userCommandService.createNewUserWithKakao(userInfo));

		return jwtTokenProvider.createAccessToken(user);
	}

	/**
	 * 카카오에서 발급 받은 인가 코드로 access token을 발행한다.
	 *
	 * @param kakaoLoginRequest access token을 발급받기 위한 정보(인가코드 등)
	 * @return access token
	 * @throws KakaoClientException 카카오 서버와의 통신 중 오류가 발생한 경우.
	 */
	private String issueAccessToken(KakaoLoginRequest kakaoLoginRequest) {
		KakaoTokenResponse accessTokenResponse = kakaoAuthClient.issueAccessToken(
			new IssueKakaoAccessTokenRequest(
				"authorization_code",
				kakaoProperties.restApiKey(),
				kakaoLoginRequest.getRedirectUri(),
				kakaoLoginRequest.getAuthorizationCode(),
				kakaoProperties.clientSecret()
			)
		);
		return accessTokenResponse.getAccessToken();
	}
}
