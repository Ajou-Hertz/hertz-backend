package com.ajou.hertz.domain.user.dto.request;

import com.ajou.hertz.common.validator.Password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdatePasswordRequest {

	@Schema(description = "비밀번호", example = "newpwd1234!!")
	@NotBlank
	@Password
	private String password;
}
