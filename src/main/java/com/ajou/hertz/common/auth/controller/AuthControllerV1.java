package com.ajou.hertz.common.auth.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.dto.request.LoginRequest;
import com.ajou.hertz.common.auth.dto.response.JwtTokenInfoResponse;
import com.ajou.hertz.common.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "로그인 등 인증 관련 API")
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@RestController
public class AuthControllerV1 {

	private final AuthService authService;

	@Operation(
		summary = "로그인",
		description = "이메일과 비밀번호를 전달받아 로그인을 진행합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "[2003] 비밀번호가 일치하지 않는 경우", content = @Content),
		@ApiResponse(responseCode = "404", description = "[2202] 이메일에 해당하는 유저를 찾을 수 없는 경우", content = @Content)
	})
	@PostMapping(value = "/login", headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public JwtTokenInfoResponse loginV1_1(@RequestBody @Valid LoginRequest loginRequest) {
		JwtTokenInfoDto jwtTokenInfoDto = authService.login(loginRequest);
		return JwtTokenInfoResponse.from(jwtTokenInfoDto);
	}
}
