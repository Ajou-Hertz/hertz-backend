package com.ajou.hertz.domain.user.controller;

import static com.ajou.hertz.global.common.constant.GlobalConstants.*;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.dto.response.UserExistenceResponse;
import com.ajou.hertz.domain.user.dto.response.UserResponse;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저 관련 API")
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserControllerV1 {

	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;

	@Operation(
		summary = "회원 존재 여부 조회",
		description = "전달받은 값들에 일치하는 회원이 존재하는지 확인힙니다."
	)
	@GetMapping(value = "/existence", headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public UserExistenceResponse getExistenceOfUserByEmailV1_1(
		@Parameter(
			description = "이메일. 입력된 이메일을 사용 중인 회원이 존재하는지 조회합니다.",
			example = "example@mail.com"
		) @RequestParam String email
	) {
		boolean existence = userQueryService.existsByEmail(email);
		return new UserExistenceResponse(existence);
	}

	@Operation(
		summary = "회원 등록",
		description = "회원을 등록합니다."
	)
	@PostMapping(headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public ResponseEntity<UserResponse> signUpV1_1(
		@RequestBody @Valid SignUpRequest signUpRequest
	) {
		UserDto userCreated = userCommandService.createNewUser(signUpRequest);
		return ResponseEntity
			.created(URI.create("/users/" + userCreated.getId()))
			.body(UserResponse.from(userCreated));
	}
}
