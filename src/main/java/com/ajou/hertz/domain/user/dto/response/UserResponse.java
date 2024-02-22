package com.ajou.hertz.domain.user.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserResponse {

	@Schema(description = "Id of user", example = "1")
	private Long id;

	@Schema(description = "이메일", example = "example@mail.com")
	private String email;

	@Schema(description = "프로필 이미지 url", example = "https://user-profile-image")
	private String profileImageUrl;

	@Schema(description = "생년월일")
	private LocalDate birth;

	@Schema(description = "성별")
	private Gender gender;

	@Schema(description = "연락 수단")
	private String contactLink;

	@Schema(description = "계정 생성일자 (가입일)")
	private LocalDateTime createdAt;

	public static UserResponse from(UserDto userDto) {
		return new UserResponse(
			userDto.getId(),
			userDto.getEmail(),
			userDto.getProfileImageUrl(),
			userDto.getBirth(),
			userDto.getGender(),
			userDto.getContactLink(),
			userDto.getCreatedAt()
		);
	}
}
