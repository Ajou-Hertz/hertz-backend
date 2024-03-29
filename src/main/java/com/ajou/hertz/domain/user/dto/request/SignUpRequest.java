package com.ajou.hertz.domain.user.dto.request;

import java.time.LocalDate;

import com.ajou.hertz.common.validator.Password;
import com.ajou.hertz.common.validator.PhoneNumber;
import com.ajou.hertz.domain.user.constant.Gender;

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
public class SignUpRequest {

	@Schema(description = "이메일", example = "test@gmail.com")
	@NotBlank
	@Email
	private String email;

	@Schema(description = "비밀번호. 8~16 글자이며 영문, 숫자, 특수문자를 각각 적어도 하나씩은 포함해야 합니다.", example = "abcd1234!@")
	@NotBlank
	@Password
	private String password;

	@Schema(description = "생년월일", example = "2024-01-01")
	private LocalDate birth;

	@Schema(description = "성별")
	private Gender gender;

	@Schema(description = "전화번호", example = "01012345678")
	@PhoneNumber
	@NotBlank
	private String phone;
}
