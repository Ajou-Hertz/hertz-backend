package com.ajou.hertz.domain.user.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserWithLinkedAccountInfoResponse extends UserResponse {

	@Schema(description = "카카오 계정 연동 여부", example = "true")
	private Boolean isKakaoAccountLinked;

	private UserWithLinkedAccountInfoResponse(
		Long id,
		String email,
		String profileImageUrl,
		LocalDate birth,
		Gender gender,
		String contactLink,
		LocalDateTime createdAt,
		Boolean isKakaoAccountLinked
	) {
		super(id, email, profileImageUrl, birth, gender, contactLink, createdAt);
		this.isKakaoAccountLinked = isKakaoAccountLinked;
	}

	public static UserWithLinkedAccountInfoResponse from(UserDto userDto) {
		return new UserWithLinkedAccountInfoResponse(
			userDto.getId(),
			userDto.getEmail(),
			userDto.getProfileImageUrl(),
			userDto.getBirth(),
			userDto.getGender(),
			userDto.getContactLink(),
			userDto.getCreatedAt(),
			StringUtils.hasText(userDto.getKakaoUid())
		);
	}
}
