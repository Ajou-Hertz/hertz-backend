package com.ajou.hertz.common.auth.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.dto.request.KakaoLoginRequest;
import com.ajou.hertz.common.auth.dto.request.LoginRequest;
import com.ajou.hertz.common.auth.dto.response.JwtTokenInfoResponse;
import com.ajou.hertz.common.auth.service.AuthService;
import com.ajou.hertz.common.kakao.service.KakaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "로그인 등 인증 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

	private final AuthService authService;
	private final KakaoService kakaoService;

	@Operation(
		summary = "로그인",
		description = "이메일과 비밀번호를 전달받아 로그인을 진행합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "[2003] 비밀번호가 일치하지 않는 경우", content = @Content),
		@ApiResponse(responseCode = "404", description = "[2202] 이메일에 해당하는 유저를 찾을 수 없는 경우", content = @Content)
	})
	@PostMapping(value = "/login", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public JwtTokenInfoResponse loginV1(@RequestBody @Valid LoginRequest loginRequest) {
		JwtTokenInfoDto jwtTokenInfoDto = authService.login(loginRequest);
		return JwtTokenInfoResponse.from(jwtTokenInfoDto);
	}

	@Operation(
		summary = "카카오 로그인",
		description = "카카오 로그인을 진행합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(
			responseCode = "409", content = @Content,
			description = """
				<p>[2200] 이미 다른 사용자가 사용 중인 이메일로 신규 회원을 등록하려고 하는 경우
				<p>[2203] 이미 다른 사용자가 사용 중인 전화번호로 신규 회원을 등록하려고 하는 경우
				"""
		),
		@ApiResponse(responseCode = "Any", description = "[10000] 카카오 서버와의 통신 중 오류가 발생한 경우. Http status code는 kakao에서 응답받은 것과 동일하게 설정하여 응답한다.", content = @Content)
	})
	@PostMapping(value = "/kakao/login", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public JwtTokenInfoResponse kakaoLoginV1(@RequestBody @Valid KakaoLoginRequest kakaoLoginRequest) {
		JwtTokenInfoDto jwtTokenInfoDto = kakaoService.login(kakaoLoginRequest);
		return JwtTokenInfoResponse.from(jwtTokenInfoDto);
	}
}
