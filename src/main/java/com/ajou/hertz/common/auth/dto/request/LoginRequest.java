package com.ajou.hertz.common.auth.dto.request;

import com.ajou.hertz.common.validator.Password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginRequest {

	@Schema(description = "이메일", example = "example@mail.com")
	@NotBlank
	@Email
	private String email;

	@Schema(description = "비밀번호", example = "1q2w3e4r!")
	@NotBlank
	@Password
	private String password;
}
