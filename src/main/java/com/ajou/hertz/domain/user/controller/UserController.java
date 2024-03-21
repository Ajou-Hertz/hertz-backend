package com.ajou.hertz.domain.user.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.auth.UserPrincipal;
import com.ajou.hertz.common.validator.PhoneNumber;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.dto.response.UserEmailResponse;
import com.ajou.hertz.domain.user.dto.response.UserExistenceResponse;
import com.ajou.hertz.domain.user.dto.response.UserResponse;
import com.ajou.hertz.domain.user.dto.response.UserWithLinkedAccountInfoResponse;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저 관련 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;

	@Operation(
		summary = "내 정보 조회",
		description = "내 정보를 조회합니다.",
		security = @SecurityRequirement(name = "access-token")
	)
	@GetMapping(value = "/me", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public UserWithLinkedAccountInfoResponse getMyInfoV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {
		UserDto userDto = userQueryService.getDtoById(userPrincipal.getUserId());
		return UserWithLinkedAccountInfoResponse.from(userDto);
	}

	@Operation(
		summary = "회원 존재 여부 조회",
		description = "전달받은 값들에 일치하는 회원이 존재하는지 확인힙니다."
	)
	@GetMapping(value = "/existence", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public UserExistenceResponse getExistenceOfUserByEmailV1(
		@Parameter(
			description = "이메일. 입력된 이메일을 사용 중인 회원이 존재하는지 조회합니다.",
			example = "example@mail.com"
		) @RequestParam @Email String email
	) {
		boolean existence = userQueryService.existsByEmail(email);
		return new UserExistenceResponse(existence);
	}

	@Operation(
		summary = "전화번호로 유저 이메일 찾기",
		description = "특정 유저를 식별할 수 있는 정보(전화번호)를 받아 일치하는 유저의 이메일을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "[2206] 전화번호에 해당하는 유저를 찾을 수 없는 경우", content = @Content)
	})
	@GetMapping(value = "/email", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public UserEmailResponse getUserEmailByPhoneV1(
		@Parameter(
			description = "이메일을 찾고자 하는 유저의 전화번호",
			example = "01012345678"
		) @RequestParam @NotBlank @PhoneNumber String phone
	) {
		UserDto userDto = userQueryService.getDtoByPhone(phone);
		return new UserEmailResponse(userDto.getEmail());
	}

	@Operation(
		summary = "회원 등록",
		description = "회원을 등록합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "409", content = @Content, description = """
			<p>[2200] 이미 다른 사용자가 사용 중인 이메일로 신규 회원을 등록하려고 하는 경우.
			<p>[2203] 이미 다른 사용자가 사용 중인 전화번호로 신규 회원을 등록하려고 하는 경우.
			""")
	})
	@PostMapping(headers = API_VERSION_HEADER_NAME + "=" + 1)
	public ResponseEntity<UserResponse> signUpV1(
		@RequestBody @Valid SignUpRequest signUpRequest
	) {
		UserDto userCreated = userCommandService.createNewUser(signUpRequest);
		return ResponseEntity
			.created(URI.create("/users/" + userCreated.getId()))
			.body(UserResponse.from(userCreated));
	}

	@Operation(
		summary = "프로필 이미지 변경",
		description = "프로필 이미지를 변경합니다.",
		security = @SecurityRequirement(name = "access-token")
	)
	@PutMapping(
		value = "/me/profile-image",
		headers = API_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public UserResponse updateProfileImageUrlV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam("profileImage") MultipartFile profileImage
	) {
		UserDto userUpdated = userCommandService.updateProfileImageUrl(userPrincipal.getUserId(), profileImage);
		return UserResponse.from(userUpdated);
	}
}
